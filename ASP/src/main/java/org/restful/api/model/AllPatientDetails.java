package org.restful.api.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public  class AllPatientDetails {
	
	private  Set<String> reasonSet = new LinkedHashSet<String>();
	private  List<DoctorsProfile> doctorsAvailablityList;
	private List<PatientAppointment> appointmentsList = new ArrayList<PatientAppointment>();
	private List<PatientReviews> patientReviewsList = new ArrayList<PatientReviews>();
	private List<PatientLabReport> patientLabReports = new ArrayList<PatientLabReport>();
	
	public List<PatientReviews> getPatientReviewsList() {
		return patientReviewsList;
	}

	public void setPatientReviewsList(List<PatientReviews> patientReviewsList) {
		this.patientReviewsList = patientReviewsList;
	}

	public List<PatientLabReport> getPatientLabReports() {
		return patientLabReports;
	}

	public void setPatientLabReports(List<PatientLabReport> patientLabReports) {
		this.patientLabReports = patientLabReports;
	}

	public List<PatientAppointment> getAppointmentsList() {
		return appointmentsList;
	}

	public void setAppointmentsList(List<PatientAppointment> appointmentsList) {
		this.appointmentsList = appointmentsList;
	}

	public  List<DoctorsProfile> getDoctorsAvailablityList() {
		return doctorsAvailablityList;
	}

	public  void setDoctorsAvailablityList(List<DoctorsProfile> doctorsAvailablityList) {
		this.doctorsAvailablityList = doctorsAvailablityList;
	}

	public  Set<String> getReasonSet() {
		return reasonSet;
	}

	public  void setReasonSet(Set<String> reasonSet) {
		this.reasonSet = reasonSet;
	}

	
	
	

}
