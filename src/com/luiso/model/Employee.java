package com.luiso.model;

import java.time.LocalDate;

/**
* Employee POJO. Object must be built thru Builder.
*
* @author  Luis Olivares
* @version 1.0
* @since   2020-05-29 
*/

public class Employee {

	private String lastName;
	
	private String firstName;
	
	private LocalDate startDate;
	
	private Address address;
	
	private int index;

	public static class EmployeeBuilder {

		private String firstName;

		private String lastName;
		
		private LocalDate startDate;

		private String address1;
		
		private String address2;
		
		private String city;

		private String state = "CA";

		private String country = "USA";

		private String zip;
	
		public EmployeeBuilder(String firstName, String lastName, LocalDate startDate) {
			if (firstName == null || firstName.trim().equals("")) {
				throw new IllegalArgumentException("First Name is required");
			}
			if (lastName == null || lastName.trim().equals("")) {
				throw new IllegalArgumentException("Last Name is required");
			}
			if (startDate == null) {
				throw new IllegalArgumentException("Start Date is required");
			}
			this.firstName = firstName;
			this.lastName = lastName;
			this.startDate = startDate;
		}
		
        public EmployeeBuilder setAddress1(String address1) {
            this.address1 = address1;
            return this;
        }
        
        public EmployeeBuilder setAddress2(String address2) {
            this.address2 = address2;
            return this;
        }

        public EmployeeBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public EmployeeBuilder setState(String state) {
			if (state != null && !state.trim().equals("")) {
				this.state = state;
			}
            return this;
        }

        public EmployeeBuilder setCountry(String country) {
			if (country != null && !country.trim().equals("")) {
				this.country = country;
			}
            return this;
        }

        public EmployeeBuilder setZip(String zip) {
            this.zip = zip;
            return this;
        }

        public Employee build() {
			return new Employee(this);
		}

	}
	
	private Employee(EmployeeBuilder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.startDate = builder.startDate;
		this.address = new Address();
		this.address.setAddress1(builder.address1);
		this.address.setAddress2(builder.address2);
		this.address.setCity(builder.city);
		this.address.setState(builder.state);
		this.address.setCountry(builder.country);
		this.address.setZip(builder.zip);		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LastName:");
		sb.append("'");
		sb.append(this.lastName);
		sb.append("',");
		sb.append("FirstName:");
		sb.append("'");
		sb.append(this.firstName);
		sb.append("',");
		sb.append("startDate:");
		sb.append("'");
		sb.append(this.startDate);
		sb.append("',");
		sb.append("index:");
		sb.append("'");
		sb.append(this.index);
		sb.append("',");
		sb.append("Address:[");
		sb.append(this.address.toString());
		sb.append("]");
		return sb.toString();
	}
	
}
