package org.restful.api.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientLabReport {
	private int memberId;
	private int doctorMemberId;
	private Date pickupDate;
	private String patientName;
	private String doctorName;
	private String errMessage;
	private String reportType;
	
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getDoctorMemberId() {
		return doctorMemberId;
	}
	public void setDoctorMemberId(int doctorMemberId) {
		this.doctorMemberId = doctorMemberId;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		String dateString = pickupDate.toString();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			this.pickupDate = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
