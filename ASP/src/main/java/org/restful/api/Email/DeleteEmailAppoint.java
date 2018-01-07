package org.restful.api.Email;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.restful.api.database.AppointmentsDb;
import org.restful.api.database.EmailNotificationsDb;
import org.restful.api.model.PatientAppointment;

public class DeleteEmailAppoint {
	
	public static void deleteEmailAppoint(PatientAppointment profile) throws Exception{
		
		   PatientAppointment data = AppointmentsDb.getDeleteProfile(profile);	
		 
			System.out.println(data.getEmail());
			// Recipient's email ID needs to be mentioned.
		      String to = data.getEmail();//change accordingly

		      // Sender's email ID needs to be mentioned
		      String from = "notifications.firsthealth@gmail.com";//change accordingly
		      final String username = "notifications.firsthealth";//change accordingly
		      final String password = "FirstHealthInsurance@786";//change accordingly

		      // Assuming you are sending email through relay.jangosmtp.net
		      String host = "smtp.gmail.com";

		      Properties props = new Properties();
		      props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.starttls.enable", "true");
		      props.put("mail.smtp.host", host);
		      props.put("mail.smtp.port", "587");
		      // Here the host is set as smtp.gmail.com and port is set as 587. Here we have enabled TLS connection.

		      // Get the Session object.
		      Session session = Session.getInstance(props,
		      new javax.mail.Authenticator() {
		         protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(username, password);
		         }
		      });

		      try {
		         // Create a default MimeMessage object.
		         Message message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));

		         // Set To: header field of the header.
		         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

		         // Set Subject: header field
		         message.setSubject("Appointment cancellation Confirmation by First Health Insurance !!");

		         // Now set the actual message sentContent for html , setText for normal text
		         message.setContent("&nbsp;&nbsp;&nbsp;&nbsp;"+
		         		            "<h1>First Health insurance</h1> ---- <em>or health care coverage</em>---------<br>"+
		        		            "<h4> Confirmation from First Health insurance for cancelling a doctor's appointment" +
		         		            "<br>"+
		         		            "------------------------------------------------------------------------------"+
		        		            "<br>"+
		         		            "<label> Patient Name : </label>" + 
		        		            "<strong><em>" + data.getPatientFirstName() + " " + data.getPatientLastName() + 
		        		            "</strong></em>" +
		        		            "<br>"+		        		            
		        		            "<label> Doctor Name : </label>" + 
		        		            "<strong><em>" + data.getDoctorName()  +
		        		            "</strong></em>" +
		        		            "<br>"+
		        		            "<label> Appointment Date and time : </label>" + 
		        		            "<strong><em>" +  data.getDateFromDb() +
		        		            "</strong></em>" +
		        		            "<br>"+
		        		            "<label> Consulting Reason : </label>" + 
		        		            "<strong><em>" + data.getReason()  +
		        		            "</strong></em>" +
		        		            "<br>"+
		        		            "------------------------------------------------------------------------------"+
		         		            "<p> Requesting you to check your health frequently with the doctor !!</p>","text/html");

		         // Send message
		         Transport.send(message);

		         System.out.println("Sent message successfully....");
		         //profile.setErrorMsg(" Email with News letter has been Sent,Please check your Inbox !....");
		         
		         

		      } catch (MessagingException e) {
		    	 
		            throw new RuntimeException(e);
		            
		      }

		}



}
