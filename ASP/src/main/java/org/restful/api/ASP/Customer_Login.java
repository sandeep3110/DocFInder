package org.restful.api.ASP;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.restful.api.Email.DeleteEmailAppoint;
import org.restful.api.Email.EmailAppointment;
import org.restful.api.database.AppointmentsDb;
import org.restful.api.model.AllPatientDetails;
import org.restful.api.model.DoctorsProfile;
import org.restful.api.model.PatientAppointment;
import org.restful.api.model.PatientReviews;

@Path("customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Customer_Login {

	@Path("reason")
	@GET

	// API to get the List of Specialties, Reasons , doctors_names
	public Response getConsultingReason() throws Exception {

		AllPatientDetails appointment = new AllPatientDetails();
		appointment.setReasonSet(AppointmentsDb.getReasonList());

		return (appointment.getReasonSet() != null) ? Response.status(200).entity(appointment).build()
				: Response.status(404).entity("No values found in Appointment_Doctor_table").build();

	}

	@Path("doctorsList")
	@POST

	// API to get the List of Specialties, Reasons , doctors_names
	public Response getDoctorsList(DoctorsProfile profile) throws Exception {

		AllPatientDetails appointment = new AllPatientDetails();
		appointment.setDoctorsAvailablityList(AppointmentsDb.getListOfDoctors(profile));
		return (appointment.getDoctorsAvailablityList().get(0).getErrMsg() == null)
				? Response.status(200).entity(appointment).build() : Response.status(404).entity(appointment).build();
	}
	
	
	@Path("bookAppoint")
	@POST
	
	//API for booking appointment for selected doctor and for selected date
	public Response bookAppointment(PatientAppointment profile) throws Exception {
		
		
		boolean confirm = AppointmentsDb.fixPatientAppointment(profile);

		 if(confirm){
			 EmailAppointment.sendEmailAppointDateAndTime(profile);
			 return Response.status(200).entity("Appointment has been booked").build();
		}else{ 
			return Response.status(404).entity("Appointment has been not booked , due to some other reasons").build();
			 }
	  }
	
	@Path("addReview")
	@PUT
	
	//API for booking appointment for selected doctor and for selected date
	public Response addReviewForDoctor(PatientReviews review) throws Exception {
		
		System.out.println(review.getDate());
		return (AppointmentsDb.addReviewForDoctor(review))? 
				    Response.status(200).entity(review).build() : Response.status(400).entity(review).build();
	  }
	
	 @Path("patientAppointmentList")
	 @POST
		
		//API for booking appointment for selected doctor and for selected date
		public Response getAppointmentList(PatientAppointment profile) throws Exception {
			
		 AllPatientDetails appointmentsList = new AllPatientDetails();
		 appointmentsList.setAppointmentsList(AppointmentsDb.getListOfAppointments(profile));
			return (appointmentsList.getAppointmentsList().get(0).getErrMessage() == null )? 
					    Response.status(200).entity(appointmentsList).build() : Response.status(400).entity(appointmentsList).build();
		  }
	
	
	 
	 @Path("delete-appointment")
	 @DELETE
		
		//API for deleting appointment for selected doctor and for selected date
		public Response deleteAppointment(PatientAppointment profile) throws Exception {
			
		 AppointmentsDb.deleteAppoint(profile);
		 
			return(profile.getErrMessage() != null )?		
				
				 Response.status(200).entity(profile).build() : Response.status(400).entity(profile).build();
			
					     
		  }
	 
	 @Path("blocked-dates")
	 @POST
		
		//API for deleting appointment for selected doctor and for selected date
		public Response blockedAppoitments(PatientAppointment profile) throws Exception {
			
		 PatientAppointment appoint =  AppointmentsDb.blockedAppoint(profile);
		 
			return(appoint.getErrMessage() == null )?		
				
				 Response.status(200).entity(appoint).build() : Response.status(400).entity(appoint).build();
			
					     
		  }
	
}
	
	


