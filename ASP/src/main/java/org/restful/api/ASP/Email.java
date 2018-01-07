package org.restful.api.ASP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.restful.api.Email.EmailNotification;
import org.restful.api.database.EmailNotificationsDb;
import org.restful.api.model.Profile;



@Path("email")
@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)

public class Email {

	
	@Path("newsLetter")
    @POST
    public Response emailTest(Profile profile) throws Exception{
		
		 /* If we are not sending profile object instead of profile.getEmail() ,
		  We are not able to set the error message in EmailNotification class because for that we need to create once again Profile object in  EmailNotification class
		  and set error message.By doing So we are getting lost of actual Profile property vaules and it will not display in this class*/
		  
		 boolean emailStatus = EmailNotification.email(profile);
		 EmailNotificationsDb emailNotify = new EmailNotificationsDb();
		 emailNotify.insertEmail(profile);
    	
    	return (emailStatus)? Response.status(200).entity(profile).build() : Response.status(409).entity(profile).build();
    }
	
}
