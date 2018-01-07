package org.restful.api.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;


import org.restful.api.Email.DeleteEmailAppoint;
import org.restful.api.model.AllPatientDetails;
import org.restful.api.model.DoctorsProfile;
import org.restful.api.model.PatientAppointment;
import org.restful.api.model.PatientReviews;

public class AppointmentsDb {

	private static Connection con = null;

	// Method to fill the data in Appointment status reason/disease/doctor name
	public static Set<String> getReasonList() throws Exception {

		Set<String> dbReasonSet = new LinkedHashSet<String>(); // To avoid
																// duplicates

		try {

			// Fetching List of disease and adding in the Set
			con = DatabaseConnection.getCon();
			PreparedStatement pst = con.prepareStatement("SELECT disease FROM appointment_doctors_list");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				dbReasonSet.add(rs.getString("disease"));
			}

			// pst.clearBatch(); // Clears the list of objects in the pst
			// statements
			rs.close(); // Close the result set to re-use it

			// Fetching List of specialties and adding in the Set

			pst = con.prepareStatement("SELECT specialty FROM appointment_doctors_list");
			rs = pst.executeQuery();
			while (rs.next()) {

				dbReasonSet.add(rs.getString("specialty"));
			}

			// pst.clearBatch(); // Clears the list of objects in the pst
			// statements
			rs.close(); // Close the result set to re-use it

			// Fetching List of Doctor names and adding in the Set

			pst = con.prepareStatement("SELECT doctor_name FROM appointment_doctors_list");
			rs = pst.executeQuery();
			while (rs.next()) {

				dbReasonSet.add(rs.getString("doctor_name"));
			}

			rs.close();
			pst.close();
			con.close();
			System.out.println(dbReasonSet);
			return dbReasonSet;
		} catch (SQLException e) {
			System.err.println("Got an exception!! in appointment_doctors_list ");
			System.err.println(e.getMessage());
			return null;
		}
	}

	// Method to get the doctors list when searched with parameters
	// reason,zipcode,carrier

	/*
	 * select * from doctor_availability_list where member_id IN ( SELECT
	 * member_id FROM appointment_doctors_list where zip_code = 19341 AND(
	 * disease = "eye" OR specialty = "eye" OR doctor_name = "eye" ))
	 */

	public static List<DoctorsProfile> getListOfDoctors(DoctorsProfile profile) throws Exception {

		List<DoctorsProfile> doctorsAvailablityList = new ArrayList<DoctorsProfile>();

		try {

			// Fetching List of Doctors and theirs address,availability,address
			// and adding in the doctorsAvailablityLists
			con = DatabaseConnection.getCon();
			PreparedStatement pst = con.prepareStatement("select * from doctor_availability_list where member_id IN "
					+ "(SELECT member_id FROM appointment_doctors_list where zip_code = ? AND ( disease = ? OR specialty = ? OR doctor_name = ?))");
			pst.setInt(1, profile.getZipcode());
			pst.setString(2, profile.getDisease());
			pst.setString(3, profile.getSpecialty());
			pst.setString(4, profile.getDoctorName());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DoctorsProfile dp = new DoctorsProfile();
				dp.setDoctorName(rs.getString("first_name"));
				dp.setRating(rs.getFloat("rating"));
				dp.setAddress(rs.getString("address"));
				dp.setAvailbleDate(rs.getDate("available_date"));
				dp.setMemberId(rs.getInt("member_id"));				
				doctorsAvailablityList.add(dp);
				System.out.println(dp.getDoctorName());
			}

			if (doctorsAvailablityList.size() == 0) {
				profile.setErrMsg(
						"Sorry no matches found for your selection !!  First Health Insurance regrets for not having doctors in your locations.");
				doctorsAvailablityList.add(profile);
				return doctorsAvailablityList;
			} else {
				rs.close();
				pst.close();
				con.close();
				return doctorsAvailablityList;
			}

		} catch (SQLException e) {
			System.err.println("Got an exception!! in appointment_doctors_list or doctor_availability_list ");
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	//Method to book Appointment for selected doctor and for selected Date
	
	public static boolean fixPatientAppointment(PatientAppointment profile) throws Exception {

		
		boolean status = false;
		/*While inserting into database I'm not initializing the Date date so it taking as null value always
		1) Take input date as string store in appointDate.
	    2) Using appointDate value convert in to Date Date by setter and getters*/
		profile.setDate(profile.getAppointDate());

		try {

			// Fetching List of Doctors and theirs address,availability,address
			// and adding in the doctorsAvailablityLists
			con = DatabaseConnection.getCon();
			PreparedStatement pst = con.prepareStatement("select * from patient_appointments where member_id = ? and doctor_member_id = ?");
			pst.setInt(1, profile.getMemberId());
			pst.setInt(2, profile.getDoctorMemberId());
			ResultSet rs = pst.executeQuery();	
			 if(rs.next()){
				 System.out.println("hello I'm updating");
				 pst.close();
				 rs.close();
				 pst = con.prepareStatement("update patient_appointments set carrier  = ? ,appointment_date = ? , reason = ? where member_id = ? "
				 		                  + "and doctor_member_id = ?");
				 pst.setString(1, profile.getCarrierName());
				 pst.setObject(2, profile.getDate());
				 pst.setString(3, profile.getReason());
				 pst.setInt(4, profile.getMemberId());
				 pst.setInt(5, profile.getDoctorMemberId());
				 int count = pst.executeUpdate();
				    if(count >0){
				    	profile.setErrMessage("Your Appointment has been successfully updated");
				    	status = true;
				    }else{
				    	profile.setErrMessage("Something went wrong please try again later!");
				    	status = false;
				    }
			 }else{
						
			    System.err.println("Im Inserting");
			    pst.close();
				rs.close();
	 	   	    pst = con.prepareStatement("INSERT INTO patient_appointments  VALUES (?,?,?,?,?,?,?,?)");
				pst.setInt(1, profile.getMemberId());
				pst.setString(2, profile.getPatientFirstName());
				pst.setString(3, profile.getPatientLastName());
				pst.setString(4, profile.getContactNum());
				pst.setString(5, profile.getCarrierName());
				pst.setObject(6, profile.getDate());
				pst.setString(7, profile.getReason());
				pst.setInt(8, profile.getDoctorMemberId());
				
				int result = pst.executeUpdate();
				/*System.out.println("hi from db class result is " + result);*/
				
				if(result > 0){
					System.out.println("Inserted");
					pst.close();
				    con.close();
				    
				    
				    status = true;
				}else{
					System.out.println("Not Inserted");
					
					status = false;
				   }
		        }
		}catch (SQLException e) {				
					System.err.println("Got an exception!! in patient_appointments ");
					System.err.println(profile.getDate());
				    System.err.println(e.getMessage());
				    
				}
		return status;
	}
	
	
	public static boolean addReviewForDoctor(PatientReviews review) throws Exception {

		boolean status;

		try {

			// Fetching List of Doctors and theirs address,availability,address
			// and adding in the doctorsAvailablityLists
			con = DatabaseConnection.getCon();
			/*PreparedStatement pst = con.prepareStatement("select * from patient_reviews where member_id = ? and doctor_member_id = ?"
					                                    +"begin  update patient_reviews set review  = ? ,review_date = ? , rating  = ?  where member_id = ? and doctor_member_id = ? end"
					                                    +"else begin insert into patient_reviews values (?,?,?,?,?) end"); */
			
			PreparedStatement pst = con.prepareStatement("select * from patient_reviews where member_id = ? and doctor_member_id = ?");
			
			pst.setInt(1, review.getMemberId());
			pst.setInt(2, review.getDoctorMemberId());
			ResultSet rs = pst.executeQuery();	
			 if(rs.next()){
				 System.out.println("hello I'm updating");
				 pst.close();
				 rs.close();
				 review.setDateIntoDb(review.getDate());
				 pst = con.prepareStatement("update patient_reviews set review  = ? ,review_date = ? , rating  = ? where member_id = ? "
				 		                  + "and doctor_member_id = ?");
				 pst.setString(1,review.getReview());
				 pst.setObject(2,review.getReviewDate());
				 pst.setFloat(3,review.getRating());
				 pst.setInt(4,review.getMemberId());
				 pst.setInt(5,review.getDoctorMemberId());
				 int count = pst.executeUpdate();
				    if(count >0){
				    	review.setErrMessage("Your Review has been successfully updated");
				    	status = true;
				    }else{
				    	review.setErrMessage("Something went wrong please try again later!");
				    	status = false;
				    }
			 }else{
				 System.out.println("hello I'm inserting");
				 pst.close();
				 rs.close();
				 review.setDateIntoDb(review.getDate());
				 
				  // getting the string value and converting to date format
				 pst = con.prepareStatement("insert into patient_reviews values (?,?,?,?,?)");
				 pst.setInt(1,review.getMemberId());
				 pst.setInt(2,review.getDoctorMemberId());
				 pst.setString(3,review.getReview());
				 pst.setObject(4,review.getReviewDate());
				 pst.setFloat(5,review.getRating());
				 int count = pst.executeUpdate();
				 if(count >0){
					 review.setErrMessage("Your Review has been successfully noted done!!");
				    	status = true;
				 }else{
				    	review.setErrMessage("Something went wrong please try again later!!");
				    	status = false;
				    }
			 }

			return status;
			

		} catch (SQLException e) {
			System.err.println("Got an exception!! in patient_reviews ");
			System.err.println(e.getMessage());
			return false;
		}
		
	}
	
	public static List<PatientAppointment> getListOfAppointments(PatientAppointment profile) throws Exception{
		
		 System.out.println(profile.getMemberId());
		List<PatientAppointment> appointmentList = new ArrayList<PatientAppointment>();
		
		try {

			// Fetching List of Doctors and theirs address,availability,address
			// and adding in the doctorsAvailablityLists
			con = DatabaseConnection.getCon();
			PreparedStatement pst = con.prepareStatement("select * from patient_appointments where member_id = ? ");
			pst.setInt(1, profile.getMemberId());
			ResultSet rs = pst.executeQuery();	
			 while(rs.next()){
				
				    PatientAppointment appointment = new PatientAppointment();
					appointment.setDateFromDb(rs.getTimestamp("appointment_date"));
					System.out.println(appointment.getDate());
					appointment.setReason(rs.getString("reason"));
					appointment.setDoctorMemberId(rs.getInt("doctor_member_id"));
					
					System.out.println("Exception here " + appointment.getDoctorMemberId());
					
					PreparedStatement pst2 = con.prepareStatement("select first_name from doctor_availability_list where member_id = ? ");
					pst2.setInt(1, rs.getInt("doctor_member_id"));
					ResultSet rs2 = pst2.executeQuery();
					while(rs2.next()){
						appointment.setDoctorName(rs2.getString("first_name"));
					}					
					
	                appointmentList.add(appointment);
	                
	                     
	                pst2.close();
	                rs2.close();
			 }
			 
			 if (appointmentList.size() == 0) {
					PatientAppointment appointment = new PatientAppointment();
					appointment.setMemberId(profile.getMemberId());
					appointment.setErrMessage("You did not have any appointments!!");
					appointmentList.add(appointment);
				}

			 pst.close();
             rs.close();
			
		}catch (SQLException e) {				
					System.err.println("Got an exception!! in patient_appointments ");
					System.err.println(profile.getDate());
				    System.err.println(e.getMessage());
				    
				}

		
		return appointmentList;
		
  }
	
	public static PatientAppointment deleteAppoint(PatientAppointment profile) throws Exception{
		
		try {

			DeleteEmailAppoint.deleteEmailAppoint(profile);
			con = DatabaseConnection.getCon();
			PreparedStatement pst = con.prepareStatement("DELETE FROM patient_appointments WHERE member_id = ? AND doctor_member_id = ?  ");
			pst.setInt(1, profile.getMemberId());
			pst.setInt(2, profile.getDoctorMemberId());
			int rs = pst.executeUpdate();	
			
			  if(rs > 0){
				  profile.setErrMessage("Your Appointment has been succefully cancelled");
			  }
			  pst.close();
			
		}catch (SQLException e) {				
					System.err.println("Got an exception!! in patient_appointments ");
					//System.err.println(profile.getDate());
				    System.err.println(e.getMessage());
				    
				}
		
		return profile;	
		
	}
	
    public static PatientAppointment getDeleteProfile(PatientAppointment profile) throws Exception{
    	
    	      PatientAppointment appointment = new PatientAppointment();
    	      System.out.println(profile.getMemberId() + " "+ profile.getDoctorMemberId());
              try {
		          	con = DatabaseConnection.getCon();
			        PreparedStatement pst = con.prepareStatement("select * FROM patient_appointments WHERE member_id = ? AND doctor_member_id = ?  ");
			        pst.setInt(1, profile.getMemberId());
			        pst.setInt(2, profile.getDoctorMemberId());
			        ResultSet rs = pst.executeQuery();	
			
			        while(rs.next()){
			        	
			        	appointment.setPatientFirstName(rs.getString("first_name"));
			        	appointment.setPatientLastName(rs.getString("last_name"));
			        	appointment.setReason(rs.getString("reason"));
			        	appointment.setDateFromDb(rs.getTimestamp("appointment_date"));
			        	
			        	System.out.println(appointment.getAppointDate());
			        	System.out.println(appointment.getPatientFirstName());
			        	System.out.println("doctor_member_id"+rs.getInt("doctor_member_id"));
			        	System.out.println("member_id" + rs.getInt("member_id"));
			        	PreparedStatement pst2 = con.prepareStatement("select first_name from doctor_availability_list where member_id = ? ");
						pst2.setInt(1, rs.getInt("doctor_member_id"));
						ResultSet rs2 = pst2.executeQuery();
						while(rs2.next()){
							appointment.setDoctorName(rs2.getString("first_name"));
						}					
			        	
						PreparedStatement pst3 = con.prepareStatement("select email from customer_table where member_id = ? ");
						System.out.println("member_id" + rs.getInt("member_id"));
						pst3.setInt(1, rs.getInt("member_id"));
						ResultSet rs3 = pst3.executeQuery();
						while(rs3.next()){
							System.out.println("email" +rs3.getString("email"));
							appointment.setEmail(rs3.getString("email"));
						}
						
						System.out.println(rs.getString("first_name"));
						System.out.println(rs.getString("last_name"));
						System.out.println(rs.getString("reason"));
						System.out.println(rs.getInt("doctor_member_id"));
						System.out.println(rs2.getString("first_name"));
						System.out.println(rs3.getString("email"));
						
						
			        	pst2.close();
			        	rs2.close();
			        	
			        	pst3.close();
			        	rs3.close();
			        	
			        }
				  
	    }catch (SQLException e) {				
					System.err.println("Got an exception!! in patient_appointments ");
					//System.err.println(profile.getDate());
				    System.err.println(e.getMessage());
				    
				}
		System.out.println("successfully sent cancellation email");
		return appointment;
    	
    	
    } 
    
    
    public static PatientAppointment blockedAppoint(PatientAppointment profile) throws Exception{
    	
    	      PatientAppointment appointment = new PatientAppointment();
    	      List<java.util.Date> blockedDates = new ArrayList<>();
    	  
    	      try {
	               	 con = DatabaseConnection.getCon();
		             PreparedStatement pst = con.prepareStatement("select appointment_date FROM patient_appointments WHERE  doctor_member_id = ? ");
		             pst.setInt(1, profile.getDoctorMemberId());
		             ResultSet rs = pst.executeQuery();	
		             
		             while(rs.next()){
		        	
		        	   appointment.setDateFromDb(rs.getTimestamp("appointment_date"));
		        	   System.out.println(rs.getTimestamp("appointment_date"));
		        	   System.out.println(appointment.getDateFromDb());
		        	   
		        	   blockedDates.add(appointment.getDateFromDb()); 
		        	   
		             }
 			  
               }catch (SQLException e) {				
				     System.err.println("Got an exception!! in patient_appointments ");
				     //System.err.println(profile.getDate());
			         System.err.println(e.getMessage());			    
			      }
    	  
    	      
    	      if(blockedDates.size() == 0){
    	    	  appointment.setErrMessage("No Dates Found for this doctor");
    	      }else{
    	    	  
    	    	  appointment.setListOfBlockedDates(blockedDates);
    	      }
		return appointment;
    	
    }
    
    
    
    
}
