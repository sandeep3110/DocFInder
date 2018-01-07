package org.restful.api.Email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.annotation.XmlRootElement;

import org.restful.api.model.Profile;

@XmlRootElement
public class EmailNotification {
	
	
	
	
@SuppressWarnings("finally")
public static boolean email(Profile profile){
	
	  
	
	  /*For detail explanation visit this site
	   "https://www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm"
	    
	    1) Adding the mail.jar and jar's from lib folder in javamail-1.4.7
         2) Adding Dependency in Maven project 
                    <groupId>javax.mail</groupId>
                      <artifactId>mail</artifactId>
                         <version>1.4.7</version>*/
		
		// Recipient's email ID needs to be mentioned.
	      String to = profile.getEmail();//change accordingly

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
	         message.setSubject("First-Health Insurance NewsLetter Subscription Successful!!");

	         // Now set the actual message sentContent for html , setText for normal text
	         message.setContent("&nbsp;&nbsp;&nbsp;&nbsp;"+
	         		            "<h1>First Health insurance</h1> ---- <em>or health care coverage</em>---------<br>"+
	         		            "<p> was affected by the Affordable Care Act -- more commonly known as \"Obamacare\" --  that was passed in 2010 with the following rollout of key initiatives,"+
	         		            " including mandatory health care coverage for all U.S. citizens and legal residents and making it illegal for insurers to reject applicants"+
	         		            " based on predetermined health conditions."+
	         		            " The law also requires small business owners with 50 or more full-time employees to offer their employees health insurance. </p>"+
	         		            
	         		            "<h1>MORE FROM THIS TOPIC</h1>"+
	         		            
	         		            "<h2> <i> 1) It Turns Out, Millennials Are Happiest With Their Health Benefits </i> </h2>"+
	         		            
	         		            "<div>"+
	         		            "<img src = \"https://assets.entrepreneur.com/content/3x2/1300/20160701051641-Doctor.jpeg?width=300&crop=4:3\">"+
	         		            "<p>"+
	         		            "Overall, 60 percent of millennials, 50 percent of baby boomers and 48 percent of gen Xers are satisfied with the ease of choosing a plan."+
	         		            "Fifty-nine percent of millennials compared with 48 percent of baby boomers and 43 percent of gen Xers are satisfied with the information that "+
	         		            "is available to help decide which plan is right for them." +
	         		            "</p>"+
	         		            "</div>"+    		            
	         		            
	         		            "<h2> <i> 2) Scientists Predict People in This Country Will Live the Longest </i></h2>"+
	         		            
	         		            "<div>"+
	         		            "<img src = \"https://assets.entrepreneur.com/content/3x2/1300/20170222204356-GettyImages-157586412.jpeg?width=300&crop=16:9\">"+
	         		            "<p>"+
	         		            "The researchers from Imperial College London, the World Health Organization, Northumbria University and the University of Washington are predicting that in 2030,"+
	         		            "the country with the highest life expectancy for women will be South Korea -- with a 90 percent probability that it will be higher than 86.7 years "+
	         		            "and a 57 percent  probability that it will be higher than 90 years -- followed by France, Japan and Spain."+
	         		            "For men, the three countries with the highest life expectancy are South Korea, Australia and Switzerland. There is a 95 percent probability that life expectancy will"+
	         		            "be higher than 80 years and a 27 percent probability that it will exceed 85 years in those countries."+ "<br><br>" +
                                "South Korea landed in the top spot because of factors such as improved childhood and adolescent nutrition, advances in medical technologies and access to healthcare. "+
                                "The high standing comes from having “maintained lower body-mass index and blood pressure than most western countries, and lower smoking in women."+
                                "</p>"+
                                "</div>"+
                                
                                "<h2> <i> 3) Slot has been Open enrollment for the individual health care plans for 2018 ,Last day to make plan selections of changes is December 08, 2017. Coverage will begin from January 1, 2018. </i> </h2>"+
                               
                                "<div>"+
                                "<img src = \"https://assets.entrepreneur.com/content/3x2/1300/1391122266-prepping-obamacare-why-not-every-business-shift-part-timers.jpg?width=300&crop=4:3\">"+
                                "<p>"+
                                "If you want to buy individual or family health insurance for 2018, now is a great time to start your planning."+
                                "<br><br>"+
                                "Under the Affordable Care Act -- more commonly known as \"Obamacare\" -- you can sign up for health insurance on Our First health insurance portal or individual marketplace only during an annual open enrollment period,"+
                                "unless you have a \"qualifying life event,\" such as getting married or having a baby."+
                                "</p>" +
                                
                                "<br>"+
                                "<h3> Five Essential points to remember </h3>"+
                                
                                "<p><b> i) You must sign up if you don't have health insurance from another source </b> </p>" +
                                "<p>  You need to sign up for health insurance during open enrollment if :</p>"+
                                "<ul>"+                                
                                "<li>You don't have health insurance through your employer or your spouse's employer.</li>"+
                                "<li>You don't have government coverage (such as veterans, Medicare and Medicaid)</li>"+
                                "<li>You're over age 26 and can no longer be on a parent's health insurance.</li>"+
                                "</ul>"+
                                
                                
                                "<p> <b> ii)  If you miss open enrollment, you may have to wait for a year to sign up </b></p>"+
                                "<p>If you miss open enrollment on your state's health insurance exchange, you won't be able to sign up for coverage unless you qualify for a special enrollment period.</p>"+
                                
                                
                                "<p><b> iii) Penalties for not having health insurance </b></p>"+
                                "<p> If went without health insurance in 2017, the penalty is 2.5 percent of your income or $695 per adult (whichever is more) and the penalty for each child in the family "+
                                "without coverage will be up to $347.50. The maximum penalty is set at $2,085. For the 2018 tax year and beyond, the penalty will remain at 2.5 percent, but the flat and"+
                                " maximum amounts will adjust for inflation. </p>"+
                                
                                "<p><b> iv) You have a choice of four levels of individual/family health insurance plans </b></p>"+
                                "<p> Plans in the health insurance marketplace are divided primarily into four categories: </p>"+
                                "<ul>"+
                                "<li> Bronze - highest out-of-pocket expenses for services (lower premiums) </li>"+
                                "<li> Silver </li>"+
                                "<li> Gold </li>"+
                                "<li> Platinum - least out-of-pocket expenses for services (higher premiums) </li>"+
                                "</ul>"+
                                
                                "<p><b> v) All health plans must cover 10 essential benefits </b></p>"+
                                "<p> All health plans, no matter the level, must provide some coverage for at least 10 essential benefits. They are: </p>"+
                                "<ul>"+
                                "<li> Outpatient care including chronic disease management </li>"+
                                "<li> Emergency care </li>"+
                                "<li> Hospitalization </li>"+
                                "<li> Pregnancy and newborn care </li>"+
                                "<li> Mental health and substance abuse services </li>"+
                                "<li> Prescription drugs </li>"+
                                "<li> Rehabilitation services and devices </li>"+
                                "<li> Lab tests </li>"+
                                "<li> Preventive and wellness services </li>"+
                                "<li> Dental and vision care for children </li>"+
                                "</ul>"+         
                                                           
                                "</div>"+
                                
                                "<div>"+
                                
                                "<h3> About This Newsletter </h3>"+
                                "<p> You received this email because you have registered with <a href = \"http://localhost:3000/login\" > First Health Insurance </a>"+
                                "If you do not want to receive this email or want to sign up for other newsletters from  First Health, manage your subscriptions  <a href = \"http://localhost:3000/login\" >here.</a>" +
                                " </p>"+
                                "<br>"+
                                "<p>"+
                                "Please do not reply to this mail. Mails sent to this account will not be monitored. Post your comments and solutions under the  <a href = \"http://localhost:3000/contactUs\">  Question of the Day forum. </a> "+
                                "</p>"+
                                "<h4>  Problems getting this mail? </h4>"+
                                "<p> To get this mail in your inbox, add notifications.firsthealth@gmail.com to your contact list.</p>"+
                                
                                "</div>" ,"text/html");

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	         profile.setErrorMsg(" Email with News letter has been Sent,Please check your Inbox !....");
	         
	         

	      } catch (MessagingException e) {
	    	 
	            throw new RuntimeException(e);
	            
	      } finally{
	    	           if(profile.getErrorMsg()!=null){
	    		              return true;
	    	              }else{
	    		               profile.setErrorMsg("Looks like this Email address doesn't Exist ,Please enter Correct email address !!");
	    		               return false;
	    	                  } 
	                } 

	}

}
