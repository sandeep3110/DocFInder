package org.restful.api.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public  class Appointments {
	
	private  Set<String> reasonSet = new LinkedHashSet<String>();
	private  List<DoctorsProfile> doctorsAvailablityList;
	private List<PatientAppointment> appointmentsList = new ArrayList<PatientAppointment>();
	
	

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
