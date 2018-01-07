package org.restful.api.model;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PatientAppointment {

	private int memberId;
	private String patientFirstName;
	private String patientLastName;
	private String carrierName;
	private String contactNum;
	private Date date;
	private Date dateFromDb;
	private int doctorMemberId;
	private String reason;
	private String errMessage;
	private String patientName;
	/*While inserting into database I'm not initializing the Date date so it taking as null value always
	1) Take input date as string store in appointDate.
    2) Using appointDate value convert in to Date Date by setter and getters*/
	private String appointDate;
	private String doctorName;
	private String email;
	private List<Date> listOfBlockedDates;
	
	
	
	
	
	public List<Date> getListOfBlockedDates() {
		return listOfBlockedDates;
	}
	public void setListOfBlockedDates(List<Date> listOfBlockedDates) {
		this.listOfBlockedDates = listOfBlockedDates;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Date getDateFromDb() {
		return dateFromDb;
	}
	public void setDateFromDb(Date dateFromDb) throws ParseException {
		String dateString = dateFromDb.toString();
		SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		                 
		this.dateFromDb = formatters.parse(dateString);
	}
	
	public String getAppointDate() {
		return appointDate;
	}
	public void setAppointDate(String appointDate) {
		this.appointDate = appointDate;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	
	public Date getDate() throws ParseException {
		
		return date;
	}
	public void setDate(String date) throws ParseException {
		
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		/*As the appointDate = "2017-11-27T11:30:18.992Z" is like this 
		four hours ahead of time we submitted in form , we need to convert to UTC*/		 
		
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.date = formatter.parse(date);
		System.out.println("I'm in model class" + this.date);
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public int getDoctorMemberId() {
		return doctorMemberId;
	}
	public void setDoctorMemberId(int doctorMemberId) {
		this.doctorMemberId = doctorMemberId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
}
