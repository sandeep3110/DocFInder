package org.restful.api.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profile {

	 private String user ;
	 private int memberId; 
	 private  String firstName;
	 private String lastName ;
	 private String specialty;
	 private int age;
	 private Double phone;
	 private String houseNumber ;
	 private String city;
	 private int zipCode;
	 private String state;	 
	 private String email;
	 private String password;
	 private String errorMsg;
	 
	 public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUser() {
			return user;
		}

	 public void setUser(String user) {
			this.user = user;
		}
	 
	 public String getSpecialty() {
			return specialty;
		}

	 public void setSpecialty(String specialty) {
			this.specialty = specialty;
		}
		
	public int getMemberId() {
			return memberId;
		}
	 
	public void setMemberId(int memberId) {
			this.memberId = memberId;
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
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public Double getPhone() {
		return phone;
	}
	
	public void setPhone(Double phone) {
		this.phone = phone;
	}
	
	public String getHouseNumber() {
		return houseNumber;
	}
	
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	 
	 
	 
	
}
