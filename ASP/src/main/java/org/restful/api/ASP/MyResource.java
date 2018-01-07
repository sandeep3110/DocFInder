package org.restful.api.ASP;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.restful.api.Email.EmailMemberId;
import org.restful.api.database.DatabaseConnection;
import org.restful.api.model.Profile;
import org.restful.api.model.Specialty;

@SuppressWarnings("unused") // Exception for warnings
@Path("myresource")

/*
 *  These are the paths we are using in this MyResource Class
 * @Path("specialty")
 * @Path("single_user")
 * @Path("authentication")*/


public class MyResource {

	//DatabaseConnection dbConn = new DatabaseConnection();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	// API for fetching the Specialty list from Helath_db
	
	@SuppressWarnings("static-access") // Exception for warnings
	@Path("specialty")
	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public Response getSpecailty() throws Exception {
		
		/*System.out.println("Hello I'm in Myresource");*/
		
		/*sl.setSpecialtyList(dbConn.getSpecialty());
		System.out.println(sl.getSpecialtyList());
		GenericEntity<List<String>> entity = new GenericEntity<List<String>>(sl.getSpecialtyList()) {};
		System.out.println(entity);
		System.out.println(Response.ok(entity).build());
	    Response.ok(entity).build();
		specialty.specialtyListMap = dbConn.getSpecialty();*/
				
		Specialty specialty = new Specialty();
		specialty.setSpecialtyList(DatabaseConnection.getSpecialty());
		return  (DatabaseConnection.getSpecialty() == null) ?  Response.status(404).entity(specialty).build() :   Response.status(200).entity(specialty).build();
		/* we will get this error - "MessageBodyWriter not found for media type=application/json, , type=class java.util.ArrayList, genericType=class java.util.ArrayList."
        Because @XMLRootElement is present on the "Specialty" class level .So, while returning the Json-response in "MyResource" class in the return "Specialty" class name should be present		    
		404 status code for Not Found , always 200 status code is good rather than other status codes because for other status codes in response on UI we will not get Json output */

	}

	// API for adding Customer/Doctor in to the Health_db
	
	@SuppressWarnings("static-access") // Exception for warnings
	@Path("single_user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public Response addProfile(Profile profile) throws Exception {
		
		String encryptedPassword = EncryptAndDecryptPassword.encrypt(profile.getPassword());
		profile.setPassword(encryptedPassword);
		
		if(profile.getUser().equalsIgnoreCase("Customer")){
		      
		      /* System.out.println(status);
		         return Response.status(200).entity("Profile has been registered")
                       .header("Access-Control-Allow-Origin", "http://localhost:3002")
                       .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                       .header("Access-Control-Allow-Headers", "application/json") 
                       .allow("OPTIONS").build(); 
                 400 Bad request - This response means that server could not understand the request due to invalid syntax.
                 200 OK The request has succeeded. The meaning of a success varies depending on the HTTP method GET , POST
                 409 Conflict - This response is sent when a request conflicts with the current state of the server. */
			 
			  boolean status = DatabaseConnection.insertData(profile);
		      if(status){
		    	  EmailMemberId.sendEmailWithMemberId(profile);
		    	  return  Response.status(200).entity("Profile has been registered !! please check your Email account for Member Id").build() ;
		    	   }
		      else{
		    	  return Response.status(409).entity(profile.getErrorMsg()).build();
		      }
		      
		  }else{ // If it is Doctor
			  
			  boolean status = DatabaseConnection.insertDoctorData(profile);
			  if(status){
				  return  Response.status(200).entity("Profile has been registered !! please check your Email account for Member Id").build() ;
			  }else{
				  return Response.status(409).entity(profile.getErrorMsg()).build();
			  }
		      
		  }	
		
	}
	
	// API for Authentication of Customer/Doctor
	
	@SuppressWarnings("static-access")
	@Path("authentication")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response userAuthentication(Profile profile) throws Exception{
		
		   /*204 No Content - There is no content to send for this request ,
		    404 Not found,
		   200 OK The request has succeeded. The meaning of a success varies depending on the HTTP method GET , POST*/	
		      // Symmetric Algorithm: Use AES/AESWrap block cipher; and
		      // Encrypt Password for security reasons
				String encryptedPassword = EncryptAndDecryptPassword.encrypt(profile.getPassword());
				profile.setPassword(encryptedPassword);
		 
		   Profile userData = DatabaseConnection.getUserProfile(profile);			   
		   
		    /* System.err.println(userData.getErrorMsg());
		     * System.out.println(userData.getErrorMsg() !=null);
		     * If we validate with userData.getErrorMsg().isEmpty();
		    we are getting an exception or error that line of code and shows java.lang.NullPointerException */		   
		   
		   return (userData.getErrorMsg() == null) ? Response.status(200).entity(userData).build() :  Response.status(404).entity(userData).build() ;		
		   
	  }
	
	
	

	
}
