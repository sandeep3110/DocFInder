package org.restful.api.ASP;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.restful.api.database.DoctorProfileDb;
import org.restful.api.database.PatientAppointmentsInfo;
import org.restful.api.model.AllPatientDetails;
import org.restful.api.model.DocTimeSlots;
import org.restful.api.model.DoctorQualifications;
import org.restful.api.model.PatientAppointment;

import com.sun.research.ws.wadl.Doc;

/**
 * @author Prashanth Avasarala Venkata
 * 
 *         This method returns the all the appointments for particular day.
 * @param {Object}
 *            patientAppointment - PatientAppointment Object which has doctorId
 *            which is used in query to return appointments of a particular
 *            doctor.
 * @return {Object} Response - appointmentsList converted into response object
 *         with status code 200 for success or 404 if the list is empty.
 */
@Path("doctor")
public class DoctorInfo {

	@Path("todayAppointments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAppointmentsForToday(PatientAppointment patientAppointment) throws Exception {
		// Creating an object for service call, to get records from the
		// database.
		PatientAppointmentsInfo patientAppointmentsInfo = new PatientAppointmentsInfo();

		// Creating an object to store the result list of appointmenst.
		// Appointments class object is return response (success:
		// appointmentList, error: reasonSet)
		AllPatientDetails appointmentsList = new AllPatientDetails();

		// Making a service call to the database and setting the results to the
		// appointmentList to return.
		appointmentsList.setAppointmentsList(
				patientAppointmentsInfo.getTodaysAppointments(new Date(), patientAppointment.getDoctorMemberId()));

		return (appointmentsList.getAppointmentsList().get(0).getErrMessage() == null)
				? Response.status(200).entity(appointmentsList).build()
				: Response.status(404).entity(appointmentsList).build();
	}

	/**
	 * @author Prashanth Avasarala Venkata
	 * 
	 *         This method returns the all the past appointments till present
	 *         day.
	 * @param {Object}
	 *            patientAppointment - PatientAppointment Object which has
	 *            doctorId which is used in query to return appointments of a
	 *            particular doctor.
	 * @return {Object} Response - appointmentsList converted into response
	 *         object with status code 200 for success or 404 if the list is
	 *         empty.
	 */
	@Path("pastAppointments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPastAppointments(PatientAppointment patientAppointment) throws Exception {
		// Creating an object for service call, to get records from the
		// database.
		PatientAppointmentsInfo patientAppointmentsInfo = new PatientAppointmentsInfo();

		// Creating an object to store the result list of appointmenst.
		// Appointments class object is return response (success:
		// appointmentList, error: reasonSet)
		AllPatientDetails appointmentsList = new AllPatientDetails();

		// Making a service call to the database and setting the results to the
		// appointmentList to return.
		appointmentsList.setAppointmentsList(
				patientAppointmentsInfo.getPastAppointments(new Date(), patientAppointment.getDoctorMemberId()));

		return (appointmentsList.getAppointmentsList().get(0).getErrMessage() == null)
				? Response.status(200).entity(appointmentsList).build()
				: Response.status(404).entity(appointmentsList).build();
	}

	/**
	 * @author Prashanth Avasarala Venkata
	 * 
	 *         This method returns all reviews of a particular doctor.
	 * @param {Object}
	 *            patientAppointment - PatientAppointment Object which has
	 *            doctorId which is used in query to return appointments of a
	 *            particular doctor.
	 * @return {Object} Response - reviewsList converted into response object
	 *         with status code 200 for success or 404 if the list is empty.
	 */
	@Path("reviews")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientReviews(PatientAppointment patientAppointment) throws Exception {
		// Creating an object for service call, to get records from the
		// database.
		PatientAppointmentsInfo patientAppointmentsInfo = new PatientAppointmentsInfo();

		// Creating an object to store the result list of appointmenst.
		// Appointments class object is return response (success:
		// appointmentList, error: reasonSet)
		AllPatientDetails appointmentsList = new AllPatientDetails();

		// Making a service call to the database and setting the results to the
		// appointmentList to return.
		appointmentsList.setPatientReviewsList(
				patientAppointmentsInfo.getPatientReviews(patientAppointment.getDoctorMemberId()));

		return (appointmentsList.getPatientReviewsList().get(0).getErrMessage() == null)
				? Response.status(200).entity(appointmentsList).build()
				: Response.status(404).entity(appointmentsList).build();
	}

	/**
	 * @author Prashanth Avasarala Venkata
	 * 
	 *         This method returns all reviews of a particular doctor.
	 * @param {Object}
	 *            patientAppointment - PatientAppointment Object which has
	 *            doctorId which is used in query to return appointments of a
	 *            particular doctor.
	 * @return {Object} Response - reviewsList converted into response object
	 *         with status code 200 for success or 404 if the list is empty.
	 */
	@Path("labReports")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPatientLabReports(PatientAppointment patientAppointment) throws Exception {
		// Creating an object for service call, to get records from the
		// database.
		PatientAppointmentsInfo patientAppointmentsInfo = new PatientAppointmentsInfo();

		// Creating an object to store the result list of appointmenst.
		// Appointments class object is return response (success:
		// appointmentList, error: reasonSet)
		AllPatientDetails patientDetails = new AllPatientDetails();
		System.out.println("hitting here");
		// Making a service call to the database and setting the results to the
		// appointmentList to return.
		patientDetails.setPatientLabReports(
				patientAppointmentsInfo.getPatientLabReports(new Date(), patientAppointment.getDoctorMemberId()));
		System.out.println(patientDetails.getPatientLabReports());
		return (patientDetails.getPatientLabReports().get(0).getErrMessage() == null)
				? Response.status(200).entity(patientDetails).build()
				: Response.status(404).entity(patientDetails).build();
	}

	/**
	 * @author Prashanth Avasarala Venkata
	 * 
	 *         This method returns doctor profile.
	 * @param {Object}
	 *            patientAppointment - PatientAppointment Object which has
	 *            doctorId which is used in query to return appointments of a
	 *            particular doctor.
	 * @return {Object} Response - reviewsList converted into response object
	 *         with status code 200 for success or 404 if the list is empty.
	 */
	@Path("doctorProfile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDoctorProfile(PatientAppointment patientAppointment) throws Exception {
		// Creating an object for service call, to get records from the
		// database.
		DoctorProfileDb docInfo = new DoctorProfileDb();

		DoctorQualifications docQualifications = docInfo.getDoctorProfile(patientAppointment.getDoctorMemberId());
		return (docQualifications.getErrMessage() == null) ? Response.status(200).entity(docQualifications).build()
				: Response.status(404).entity(docQualifications).build();
	}

	/**
	 * @author Prashanth Avasarala Venkata
	 * 
	 *         This method updates doctor profile.
	 * @param {Object}
	 *            patientAppointment - PatientAppointment Object which has
	 *            doctorId which is used in query to return appointments of a
	 *            particular doctor.
	 * @return {Object} Response - reviewsList converted into response object
	 *         with status code 200 for success or 404 if the list is empty.
	 */
	@Path("updateProfile")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDoctorProfile(DoctorQualifications doctorQualifications) throws Exception {
		DoctorProfileDb docInfo = new DoctorProfileDb();

		DoctorQualifications result = docInfo.updateDoctorProfile(doctorQualifications);
		return (result.getSuccessMessage() != null) ? Response.status(200).entity(result).build()
				: Response.status(500).entity(result).build();
	}
	
	
	@Path("addTimeSlots")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDocTimeSlots(DocTimeSlots docTimeSlots) throws Exception {
		
//		System.out.println("timeslots" + docTimeSlots.getDoctorSchedule().size());
//		
//        for (Date slot : docTimeSlots.getDoctorSchedule()) {
//			System.out.println(slot);
//		}
        
		DoctorProfileDb docInfo = new DoctorProfileDb();

		 DocTimeSlots result = docInfo.addDocTimeSlots(docTimeSlots);
		 		 
		return (result.getSuccessMessage() != null) ? Response.status(200).entity(result).build()
				: Response.status(500).entity(result).build();
	}
	
	@Path("getTimeSlots")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDocTimeSlots(DocTimeSlots docTimeSlots) throws Exception {
		
		System.out.println("timeslots" + docTimeSlots.getDoctorMemberId());
		DoctorProfileDb docInfo = new DoctorProfileDb();

		DocTimeSlots result = docInfo.getDocTimeSlots(docTimeSlots);
		 		 
		return (result.getDoctorSchedule().size() > 0) ? Response.status(200).entity(result).build()
				: Response.status(500).entity(result).build();
	}
}
