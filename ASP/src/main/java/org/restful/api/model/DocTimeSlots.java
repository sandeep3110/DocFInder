package org.restful.api.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocTimeSlots {

	private int doctorMemberId;
	private List<Date> doctorSchedule;
	private String errMessage;
	private String successMessage;

	public String getErrorMessage() {
		return errMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public int getDoctorMemberId() {
		return doctorMemberId;
	}

	public void setDoctorMemberId(int doctorMemberId) {
		this.doctorMemberId = doctorMemberId;
	}

	public List<Date> getDoctorSchedule() {
		return doctorSchedule;
	}

	public void setDoctorSchedule(List<Date> doctorSchedule) {
		this.doctorSchedule = doctorSchedule;
	}

}
