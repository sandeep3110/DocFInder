package org.restful.api.model;

public class DoctorQualifications {

	private String education;
	private int doctorMemberId;
	private String doctorFirstName;
	private String doctorLastName;
	private String hospitalAffliation;
	private String languagesSpoken;
	private String professionalMemberships;
	private String boardCertification;
	private String affiliatedInsurance;
	private String specialities;
	private String errMessage;
	private String successMessage;
	private boolean profileExists;
	
	public boolean isProfileExists() {
		return profileExists;
	}

	public void setProfileExists(boolean profileExists) {
		this.profileExists = profileExists;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public int getDoctorMemberId() {
		return doctorMemberId;
	}

	public void setDoctorMemberId(int doctorMemberId) {
		this.doctorMemberId = doctorMemberId;
	}

	public String getHospitalAffliation() {
		return hospitalAffliation;
	}

	public void setHospitalAffliation(String hospitalAffliation) {
		this.hospitalAffliation = hospitalAffliation;
	}

	public String getLanguagesSpoken() {
		return languagesSpoken;
	}

	public void setLanguagesSpoken(String languagesSpoken) {
		this.languagesSpoken = languagesSpoken;
	}

	public String getProfessionalMemberships() {
		return professionalMemberships;
	}

	public void setProfessionalMemberships(String professionalMemberships) {
		this.professionalMemberships = professionalMemberships;
	}

	public String getBoardCertification() {
		return boardCertification;
	}

	public void setBoardCertification(String boardCertification) {
		this.boardCertification = boardCertification;
	}

	public String getAffiliatedInsurance() {
		return affiliatedInsurance;
	}

	public void setAffiliatedInsurance(String affiliatedInsurance) {
		this.affiliatedInsurance = affiliatedInsurance;
	}

	public String getSpecialities() {
		return specialities;
	}

	public void setSpecialities(String specialities) {
		this.specialities = specialities;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}
}
