package com.luiso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import com.luiso.model.Employee;

/**
* Test FileConverter.
*
* @author  Luis Olivares
* @version 1.0
* @since   2020-05-29 
*/

public class FileConverterTest {

	final private static String EMPLOYEE_DATA_COMMA_SEPARATED = "Luis,Olivares,20200501,123 X St,Apt K,Bayonne,NJ,USA,07002";

	final private static String EMPLOYEE_DATA_FIXED_LENGTH = "Luis      Olivares         20200501123 X St  Apt K     Bayonne   NJUSA07002     ";

	final private static String EMPLOYEE_DATA_FIXED_LENGTH_NO_STATE_NO_COUNTRY = "Luis      Olivares         20200501123 X St  Apt K     Bayonne        07002     ";

	final private static String EMPLOYEE_DATA_FIXED_LENGTH_NO_FIRST_NAME = "          Olivares         20200501123 X St  Apt K     Bayonne        07002     ";

	final private static String EMPLOYEE_DATA_FIXED_LENGTH_INCORRECT_FORMAT = "Luis      Olivares         20200501123 X St  Apt K     Bayonne   NJUSA";

	final private static String FIRST_NAME = "Luis";

	final private static String LAST_NAME = "Olivares";

	final private static LocalDate START_DATE = LocalDate.parse("20200501", DateTimeFormatter.ofPattern(FileConverter.INPUT_DATE_FORMAT));

	final private static String ADDRESS_1 = "123 X St";

	final private static String ADDRESS_2 = "Apt K";

	final private static String CITY = "Bayonne";

	final private static String STATE = "NJ";

	final private static String COUNTRY = "USA";

	final private static String ZIP = "07002";

	final private static String DEFAULT_STATE = "CA";

	final private static String DEFAULT_COUNTRY = "USA";

	@Before
	public void setUp() {		
	}
	
	@Test
	public void whenCreatingEmployeeFromCSV_CreateSuccessfully() {
		String[] values = EMPLOYEE_DATA_COMMA_SEPARATED.split(",");
		Employee employee = FileConverter.createEmployee(values);
		assertEquals(9, values.length);
		assertNotNull(employee);
		assertEquals(FIRST_NAME, employee.getFirstName());
		assertEquals(LAST_NAME, employee.getLastName());
		assertEquals(START_DATE, employee.getStartDate());
		assertNotNull(employee.getAddress());
		assertEquals(ADDRESS_1, employee.getAddress().getAddress1());
		assertEquals(ADDRESS_2, employee.getAddress().getAddress2());
		assertEquals(CITY, employee.getAddress().getCity());
		assertEquals(STATE, employee.getAddress().getState());
		assertEquals(COUNTRY, employee.getAddress().getCountry());
		assertEquals(ZIP, employee.getAddress().getZip());
	}
	
	@Test
	public void whenCreatingEmployeeFromFixedLengthData_CreateSuccessfully() {
		String[] values = FileConverter.splitString(EMPLOYEE_DATA_FIXED_LENGTH, FileConverter.SPLIT_REGEX);
		Employee employee = FileConverter.createEmployee(values);
		assertEquals(9, values.length);
		assertNotNull(employee);
		assertEquals(FIRST_NAME, employee.getFirstName());
		assertEquals(LAST_NAME, employee.getLastName());
		assertEquals(START_DATE, employee.getStartDate());
		assertNotNull(employee.getAddress());
		assertEquals(ADDRESS_1, employee.getAddress().getAddress1());
		assertEquals(ADDRESS_2, employee.getAddress().getAddress2());
		assertEquals(CITY, employee.getAddress().getCity());
		assertEquals(STATE, employee.getAddress().getState());
		assertEquals(COUNTRY, employee.getAddress().getCountry());
		assertEquals(ZIP, employee.getAddress().getZip());
	}

	@Test
	public void whenCreatingEmployeeFromFixedLength_NoStateNoCountry_CreateSuccessfullyWithDefaults() {
		String[] values = FileConverter.splitString(EMPLOYEE_DATA_FIXED_LENGTH_NO_STATE_NO_COUNTRY, FileConverter.SPLIT_REGEX);
		Employee employee = FileConverter.createEmployee(values);
		assertEquals(9, values.length);
		assertNotNull(employee);
		assertEquals(FIRST_NAME, employee.getFirstName());
		assertEquals(LAST_NAME, employee.getLastName());
		assertEquals(START_DATE, employee.getStartDate());
		assertNotNull(employee.getAddress());
		assertEquals(ADDRESS_1, employee.getAddress().getAddress1());
		assertEquals(ADDRESS_2, employee.getAddress().getAddress2());
		assertEquals(CITY, employee.getAddress().getCity());
		assertEquals(DEFAULT_STATE, employee.getAddress().getState());
		assertEquals(DEFAULT_COUNTRY, employee.getAddress().getCountry());
		assertEquals(ZIP, employee.getAddress().getZip());
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenCreatingEmployeeFromFixedLength_NoFirstName_ThrowsException() {
		String[] values = FileConverter.splitString(EMPLOYEE_DATA_FIXED_LENGTH_NO_FIRST_NAME, FileConverter.SPLIT_REGEX);
		Employee employee = FileConverter.createEmployee(values);
		assertEquals(9, values.length);
		assertNotNull(employee);
		assertEquals(FIRST_NAME, employee.getFirstName());
		assertEquals(LAST_NAME, employee.getLastName());
		assertEquals(START_DATE, employee.getStartDate());
		assertNotNull(employee.getAddress());
		assertEquals(ADDRESS_1, employee.getAddress().getAddress1());
		assertEquals(ADDRESS_2, employee.getAddress().getAddress2());
		assertEquals(CITY, employee.getAddress().getCity());
		assertEquals(STATE, employee.getAddress().getState());
		assertEquals(COUNTRY, employee.getAddress().getCountry());
		assertEquals(ZIP, employee.getAddress().getZip());
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenCreatingEmployeeFromFixedLength_IncorrectFormat_ThrowsException() {
		FileConverter.splitString(EMPLOYEE_DATA_FIXED_LENGTH_INCORRECT_FORMAT, FileConverter.SPLIT_REGEX);
	}

}
