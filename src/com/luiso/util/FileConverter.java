package com.luiso.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.luiso.model.Employee;

/**
* Fetch data from an input file and generate an XML or plain text file.
*
* @author  Luis Olivares
* @version 1.0
* @since   2020-05-29 
*/

public class FileConverter {

	final private static String FILE_TYPE_1= "1";

	final private static String FILE_TYPE_2= "2";

	final public static String INPUT_DATE_FORMAT= "yyyyMMdd";

	final private static String OUTPUT_DATE_FORMAT= "MMMM dd, yyyy";

	final public static String SPLIT_REGEX = "^(.{10})(.{17})(.{8})(.{10})(.{10})(.{10})(.{2})(.{3})(.{10})$";

	enum SortOrder {
		LAST_NAME,
		FIRST_NAME,
		START_DATE
	}

	
	/**
	* Fetch Employee data from an input file and generate an XML or plain text file, 
	* sorted as per the sortBy parameter.
	* @param args [inputFileName] [outputFileName] [sortBy] [formatted] [delimiter].
	* @return NA.
	* @exception Exception On error.
	* @see Exception
	*/
	public static void main(String[] args) {

		if (args.length < 4) {
			System.out.println("Incorrect number of parameters");
			System.out.println("");
			System.out.println("Usage:");
			System.out.println("jar FileConverter [inputFileName] [outputFileName] [sortBy] [formatted] [delimiter]");	
			System.out.println("");
			System.out.println("Arguments:");
			System.out.println("[inputFileName] Input plain text file name (absolute path). First line must specify File Type ('1' or '2').");
			System.out.println("[outputFileName] Output file name (absolute path).");
			System.out.println("[sortBy] 'FIRST_NAME' or  'LAST_NAME' or 'START_DATE' values. Data in output file will be sorted in ascending order based on this field.");
			System.out.println("[formatted] 'true' or 'false'. if true, returns data formatted as plain text.");
			System.out.println("[delimiter] Optional argument. Default is comma (',') if not specified. Delimiter to be used in case of File Type '2'only.");
			System.out.println("");
			System.out.println("Example:");
			System.out.println("jar C:\\Tests\\TestFile_2.txt C:\\Tests\\output.xml FIRST_NAME false");
			System.exit(1);
		}

		String inputFileName = args[0];
		String outputFileName = args[1];
		String sortBy = args[2];
		boolean formatted = Boolean.valueOf(args[3]);			
		String delimiter = ",";

		if (args.length >= 5) {
			delimiter = args[4];
		}
		
		List<Employee> employeesList;
		
		try {
			employeesList = getEmployeesListFromFile(inputFileName, sortBy, delimiter);
			if (formatted) {
				generateFormattedFile(outputFileName, employeesList);				
			} else {
				generateXmlFile(outputFileName, employeesList);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("File generated successfully.");

	}
	
	
	/**
	* Fetch Employee data from an input file and store it into a List, 
	* sorted as per the sortBy parameter.
	* @param inputFileName Input filename to be used.
	* @param sortBy Employee field to be used for sorting.
	* @param delimiter Delimiter to be used to fetch data only in case of File Type '2'.
	* @return List<Employee> A list of Employees, sorted as per the sortBy parameter.
	* @exception IOException On IO error.
	* @see IOException
	*/
	private static List<Employee> getEmployeesListFromFile(String fileName, String sortBy, String delimiter) throws IOException {
				
		List<Employee> employeesList = new ArrayList<Employee>();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			String fileType = scanner.nextLine();
			if (fileType.equals(FILE_TYPE_1)) {
				while (scanner.hasNextLine()) {
				    String[] values = splitString(scanner.nextLine(), SPLIT_REGEX);
					employeesList.add(createEmployee(values));
				}
			} else if (fileType.equals(FILE_TYPE_2)) {
				while (scanner.hasNextLine()) {
				    String[] values = scanner.nextLine().split(delimiter);
					employeesList.add(createEmployee(values));
				}
			} else {
				throw new IOException("Invalid file type or format");
			}
			
			switch(SortOrder.valueOf(sortBy)) {
		      case LAST_NAME:
		    	  employeesList.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
		        break;
		      case FIRST_NAME:
		    	  employeesList.sort((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
		        break;
		      case START_DATE:
		    	  employeesList.sort((o1, o2) -> o1.getStartDate().compareTo(o2.getStartDate()));
		        break;
			}
			
		} catch(NoSuchElementException nsee) {
			throw new NoSuchElementException("Invalid file format");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
		return employeesList;
	}
	
	
	/**
	* Return a String array containing the results of the regex matches.
	* @param text String to be processed by the regex.
	* @param regex Regex to apply.
	* @return String[] String array containing the results of the regex matches.
	* @exception IllegalArgumentException If pattern returns no match.
	*/
	public static String[] splitString(String text, String regex) {
		List<String> valuesList = new ArrayList<>();
		Matcher matcher =  Pattern.compile(regex).matcher(text);
		if (matcher.matches()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				valuesList.add(matcher.group(i).trim()); 
			}
		} else {
			throw new IllegalArgumentException("Arguments return no results");
		}
	    return valuesList.toArray(new String[0]);
	}
	
	
	/**
	* Create and return an Employee object from a String array.
	* @param values String array with the Employee's data.
	* @return Employee An Employee.
	* @see Employee
	*/
	public static Employee createEmployee(String[] values) {
		return new Employee.EmployeeBuilder(
				values[0],
				values[1],
				LocalDate.parse(values[2], DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT))
						)
		.setAddress1(values[3])
		.setAddress2(values[4])
		.setCity(values[5])
		.setState(values[6])
		.setCountry(values[7])
		.setZip(values[8])
		.build();

	}

	
	/**
	* Generate a plain text formatted file including all employees on the list.
	* @param outputFileName Output filename to be generated.
	* @param employeesList The list of Employees to be included on the file.
	* @return NA.
	* @exception FileNotFoundException On IO error.
	* @see FileNotFoundException
	*/
	private static void generateFormattedFile(String outputFileName, List<Employee> employeesList) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(outputFileName);
		int i = 0;
		for (Employee employee: employeesList) {
			employee.setIndex(++i);
			out.println(formatEmployeeData(employee));
		}
		out.close();
	}
	
	
	/**
	* Return a String with the Employee's formatted data.
	* @param employee An Employee.
	* @return String A String with the Employee's formatted data.
	*/
	private static String formatEmployeeData(Employee employee) {
		StringBuilder sb = new StringBuilder();
		sb.append(employee.getIndex());
		sb.append("\n");
		sb.append(employee.getFirstName());
		sb.append(" ");
		sb.append(employee.getLastName());
		sb.append(" (");
		sb.append(DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT).format(employee.getStartDate()));
		sb.append(")\n");
		sb.append(employee.getAddress().getAddress1());
		sb.append(", ");
		sb.append(employee.getAddress().getAddress2());
		sb.append("\n");
		sb.append(employee.getAddress().getCity());
		sb.append(" ");
		sb.append(employee.getAddress().getState());
		sb.append("\n");
		sb.append(employee.getAddress().getCountry());
		sb.append(", ");
		sb.append(employee.getAddress().getZip());
		sb.append("\n");

		return sb.toString();
	}

	
	/**
	* Generate XML file including all employees on the list.
	* @param outputFileName Output filename to be generated.
	* @param employeesList The list of Employees to be included on the file.
	* @return NA.
	* @exception IOException, TransformerException, ParserConfigurationExceptio On IO error.
	*/
	private static void generateXmlFile(String outputFileName, List<Employee> employeesList) throws IOException, TransformerException, ParserConfigurationException {

        DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = dFact.newDocumentBuilder();
        Document doc = build.newDocument();

        Element root = doc.createElement("Employees");
        doc.appendChild(root);

        int i = 0;
        for (Employee employee : employeesList) {
        	employee.setIndex(++i);

        	Element emp = doc.createElement("Employee");
            root.appendChild(emp);
            
    		Element index = doc.createElement("Index");
    		index.appendChild(doc.createTextNode(String.valueOf(employee.getIndex())));
            emp.appendChild(index);

            Element firstName = doc.createElement("FirstName");
    		firstName.appendChild(doc.createTextNode(employee.getFirstName()));
            emp.appendChild(firstName);

            Element lastName = doc.createElement("LastName");
    		lastName.appendChild(doc.createTextNode(employee.getLastName()));
            emp.appendChild(lastName);

        	Element	startDate = doc.createElement("StartDate");
    		startDate.appendChild(doc.createTextNode(DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT).format(employee.getStartDate())));
            emp.appendChild(startDate);

            Element address1 = doc.createElement("Address1");
            address1.appendChild(doc.createTextNode(employee.getAddress().getAddress1()));
            emp.appendChild(address1);

            Element address2 = doc.createElement("Address2");
            address2.appendChild(doc.createTextNode(employee.getAddress().getAddress2()));
            emp.appendChild(address2);

            Element city = doc.createElement("City");
            city.appendChild(doc.createTextNode(employee.getAddress().getCity()));
            emp.appendChild(city);

            Element state = doc.createElement("State");
            state.appendChild(doc.createTextNode(employee.getAddress().getState()));
            emp.appendChild(state);

            Element country = doc.createElement("Country");
            country.appendChild(doc.createTextNode(employee.getAddress().getCountry()));
            emp.appendChild(country);

            Element zip = doc.createElement("Zip");
            zip.appendChild(doc.createTextNode(employee.getAddress().getZip()));
            emp.appendChild(zip);

        }

        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

        aTransformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "4");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        Writer writer = new PrintWriter(outputFileName);
        StreamResult result = new StreamResult(writer);
        aTransformer.transform(source, result);
	}
}