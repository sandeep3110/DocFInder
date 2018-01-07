package org.restful.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.restful.api.model.PatientAppointment;
import org.restful.api.model.PatientLabReport;
import org.restful.api.model.PatientReviews;

public class PatientAppointmentsInfo {

	public List<PatientAppointment> getTodaysAppointments(Date date, int doctorMemberId) throws Exception {
		List<PatientAppointment> appointmentList = new ArrayList<PatientAppointment>(); // Creating an object to store the appointmentList.
		Connection conn = DatabaseConnection.getCon(); // Creating a database connection to execute query.
		PreparedStatement query = null;
		ResultSet result = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(date) + " 00:00";
	//	String dayEnd = formatter.format(date) + " 23:59";

		try {
			query = conn.prepareStatement(
					"SELECT * FROM health_db.patient_appointments where appointment_date >= ? AND doctor_member_id=?");
			/*System.out.println(today);
			System.out.println(dayEnd);*/
			query.setObject(1, today);
//			query.setObject(2, dayEnd);
			query.setInt(2, doctorMemberId);

			result = query.executeQuery();

			while (result.next()) {
				PatientAppointment appointment = new PatientAppointment();
				appointment.setMemberId(result.getInt("member_id"));
				appointment.setPatientFirstName(result.getString("first_name"));
				appointment.setPatientLastName(result.getString("last_name"));
				appointment.setContactNum(result.getString("contact_num"));
				appointment.setCarrierName(result.getString("carrier"));
				/*System.out.println(result.getTimestamp("appointment_date"));
				System.out.println(result.getTimestamp("appointment_date").toString());*/
				appointment.setDateFromDb(result.getTimestamp("appointment_date"));
				//System.out.println(appointment.getDate());
				appointment.setReason(result.getString("reason"));
				appointment.setDoctorMemberId(result.getInt("doctor_member_id"));
                appointment.setPatientName(appointment.getPatientFirstName() + " " + appointment.getPatientLastName());
				appointmentList.add(appointment);
				System.out.println(appointment.getMemberId());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		if (appointmentList.size() == 0) {
			PatientAppointment appointment = new PatientAppointment();
			appointment.setDoctorMemberId(doctorMemberId);
			appointment.setErrMessage("No Appointments for today");
			appointmentList.add(appointment);
		}

		return appointmentList;

	}
	
	public List<PatientAppointment> getPastAppointments(Date date, int doctorMemberId) throws Exception {
		List<PatientAppointment> appointmentList = new ArrayList<PatientAppointment>(); // Creating an object to store the appointmentList.
		Connection conn = DatabaseConnection.getCon(); // Creating a database connection to execute query.
		PreparedStatement query = null;
		ResultSet result = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(date) + " 00:00";

		try {
			query = conn.prepareStatement(
					"SELECT * FROM health_db.patient_appointments where appointment_date < ? AND doctor_member_id=?");
			query.setObject(1, today);
			query.setInt(2, doctorMemberId);

			result = query.executeQuery();

			while (result.next()) {
				PatientAppointment appointment = new PatientAppointment();
				appointment.setMemberId(result.getInt("member_id"));
				appointment.setPatientFirstName(result.getString("first_name"));
				appointment.setPatientLastName(result.getString("last_name"));
				appointment.setContactNum(result.getString("contact_num"));
				appointment.setCarrierName(result.getString("carrier"));
				appointment.setDateFromDb(result.getTimestamp("appointment_date"));
				appointment.setReason(result.getString("reason"));
				appointment.setDoctorMemberId(result.getInt("doctor_member_id"));
                appointment.setPatientName(appointment.getPatientFirstName() + " " + appointment.getPatientLastName());
				appointmentList.add(appointment);
				System.out.println(appointment.getMemberId());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		if (appointmentList.size() == 0) {
			PatientAppointment appointment = new PatientAppointment();
			appointment.setDoctorMemberId(doctorMemberId);
			appointment.setErrMessage("No Appointments for today");
			appointmentList.add(appointment);
		}

		return appointmentList;

	}
	
	
	public List<PatientReviews> getPatientReviews(int doctorMemberId) throws Exception {
		List<PatientReviews> patientReviews = new ArrayList<PatientReviews>();

		Connection conn = DatabaseConnection.getCon();
		PreparedStatement query = null;
		ResultSet result = null;
		String doctorName;
		String patientName;

		try {
			query = conn.prepareStatement("SELECT * FROM health_db.patient_reviews where doctor_member_id=?");
			query.setInt(1, doctorMemberId);

			result = query.executeQuery();
			doctorName = getDoctorName(doctorMemberId);

			while (result.next()) {
				patientName = getPatientName(result.getInt("member_id"));

				PatientReviews review = new PatientReviews();
				review.setDoctorMemberId(result.getInt("doctor_member_id"));
				review.setMemberId(result.getInt("member_id"));
				review.setReview(result.getString("review"));
				review.setReviewDate(result.getTimestamp("review_date"));
				review.setDoctorName(doctorName);
				review.setPatientName(patientName);
				review.setRating(result.getFloat("rating"));
				patientReviews.add(review);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		if (patientReviews.size() == 0) {
			PatientReviews review = new PatientReviews();
			review.setDoctorMemberId(doctorMemberId);
			review.setErrMessage("No Reviews!");
			patientReviews.add(review);
		}

		return patientReviews;
	}

	public List<PatientLabReport> getPatientLabReports(Date date, int doctorMemberId) throws Exception {
		List<PatientLabReport> patientLabReports = new ArrayList<PatientLabReport>();

		Connection conn = DatabaseConnection.getCon();
		PreparedStatement query = null;
		ResultSet result = null;
		String doctorName;
		String patientName;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(date) + " 00:00";

		try {
			query = conn.prepareStatement(
					"SELECT * FROM health_db.patient_lab_reports where doctor_member_id=? AND date >= ?");
			query.setInt(1, doctorMemberId);
			query.setObject(2, today);

			result = query.executeQuery();
			doctorName = getDoctorName(doctorMemberId);

			while (result.next()) {
				patientName = getPatientName(result.getInt("member_id"));

				PatientLabReport labReport = new PatientLabReport();
				labReport.setDoctorMemberId(result.getInt("doctor_member_id"));
				labReport.setDoctorName(doctorName);
				labReport.setPatientName(patientName);
				labReport.setMemberId(result.getInt("member_id"));
				labReport.setPickupDate(result.getTimestamp("date"));
				labReport.setReportType(result.getString("type"));
				patientLabReports.add(labReport);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		if (patientLabReports.size() == 0) {
			PatientLabReport labReport = new PatientLabReport();
			labReport.setDoctorMemberId(doctorMemberId);
			labReport.setErrMessage("No Lab Reports for PickUp!");
			patientLabReports.add(labReport);
		}

		return patientLabReports;
	}

	public String getDoctorName(int doctorMemberId) throws Exception {
		Connection conn = DatabaseConnection.getCon();
		PreparedStatement query = null;
		ResultSet result = null;
		String doctorName = null;

		try {
			query = conn.prepareStatement("SELECT first_name, last_name FROM health_db.doctor_table where member_id=?");

			query.setInt(1, doctorMemberId);
			result = query.executeQuery();

			while (result.next()) {
				doctorName = result.getString("first_name") + " " + result.getString("last_name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return doctorName;
	}

	public String getPatientName(int patientMemberId) throws Exception {
		Connection conn = DatabaseConnection.getCon();
		PreparedStatement query = null;
		ResultSet result = null;
		String patientName = null;

		try {
			query = conn.prepareStatement(
					"SELECT first_name, last_name FROM health_db.patient_appointments where member_id=?");

			query.setInt(1, patientMemberId);
			result = query.executeQuery();

			while (result.next()) {
				patientName = result.getString("first_name") + " " + result.getString("last_name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return patientName;
	}

	

	public boolean addAppointments(PatientAppointment patientAppointment) throws Exception {
		Connection conn = DatabaseConnection.getCon();
		PreparedStatement query = null;
		boolean result = false;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String today = formatter.format(patientAppointment.getDate());

		try {
			query = conn.prepareStatement("Insert into health_db.patient_appointments values (?, ?, ?, ?, ?, ?)");

			query.setInt(1, patientAppointment.getMemberId());
			query.setString(2, patientAppointment.getPatientFirstName());
			query.setString(3, patientAppointment.getPatientLastName());
			query.setString(4, patientAppointment.getContactNum());
			query.setString(5, patientAppointment.getCarrierName());
			//query.setObject(6, today);
			query.setString(7, patientAppointment.getReason());
			query.setInt(8, patientAppointment.getDoctorMemberId());

			result = query.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}
}
