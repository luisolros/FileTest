package com.luiso.model;

/**
* Address POJO.
*
* @author  Luis Olivares
* @version 1.0
* @since   2020-05-29 
*/

public class Address {

	private String address1;
	
	private String address2;
	
	private String city;

	private String state;

	private String country;

	private String zip;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Address1:");
		sb.append("'");
		sb.append(this.address1);
		sb.append("',");
		sb.append("Address2:");
		sb.append("'");
		sb.append(this.address2);
		sb.append("',");
		sb.append("City:");
		sb.append("'");
		sb.append(this.city);
		sb.append("',");
		sb.append("State:");
		sb.append("'");
		sb.append(this.state);
		sb.append("',");
		sb.append("Country:");
		sb.append("'");
		sb.append(this.country);
		sb.append("',");
		sb.append("Zip:");
		sb.append("'");
		sb.append(this.zip);
		sb.append("'");
		return sb.toString();
	}

}
