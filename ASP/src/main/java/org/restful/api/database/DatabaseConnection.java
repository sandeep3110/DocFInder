package org.restful.api.database;
import org.restful.api.ASP.EncryptAndDecryptPassword;
import org.restful.api.model.Profile;
import org.restful.api.model.Specialty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class DatabaseConnection {

	private static Connection con = null;
	
	
	public static Connection getCon()throws Exception {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/health_db"; 
			String user="root";
			String password="";
			con= DriverManager.getConnection(url, user, password);
	    }catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}

	/*public static Map<String,Profile> getProfile(String name) throws Exception{
		
		  try {
		       con = getCon();
		       //Statement stmt=con.createStatement();
		       System.err.println("Im In");
	    	   //ResultSet rs=stmt.executeQuery("select * from record where name ='"+ name +"'");
	    	   PreparedStatement pst = con.prepareStatement("select * from sample where name = ?");
				pst.setString(1, name);
				ResultSet rs = pst.executeQuery();
	    	
	    	while(rs.next()){
	    		Profile p = new Profile();
	    		 p.setName(rs.getString("name"));
	    		 p.setAge(rs.getInt("age"));
	    		 p.setPhone(rs.getDouble("phone"));
	    		 p.setPlace(rs.getString("country"));
	    		profileMap.put(name, p);
	    	}
	    	 
			
		} catch (SQLException e) {
			
			System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
		}
		  
		  return profileMap;
	    
	}*/
	
	// Method for inserting customer
	
	public static boolean insertData(Profile profile) throws Exception {
		  System.out.println(profile);
		  try{
		    con = getCon();
		    System.err.println("Im Inserting");
 	   	   PreparedStatement pst = con.prepareStatement("INSERT INTO customer_table  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, profile.getMemberId());
			pst.setString(2, profile.getUser());
			pst.setString(3,profile.getFirstName());
			pst.setString(4,profile.getLastName());
			pst.setInt(5, profile.getAge() );
			pst.setDouble(6,profile.getPhone());
			pst.setString(7, profile.getHouseNumber());
			pst.setString(8,profile.getCity());
			pst.setInt(9, profile.getZipCode());
			pst.setString(10,profile.getState());
			pst.setString(11,profile.getEmail());
			pst.setString(12,profile.getPassword());
			
			int result = pst.executeUpdate();
			/*System.out.println("hi from db class result is " + result);*/
			pst.close();
			con.close();
			return (result > 0)? true : false;
	        }catch (SQLException e) {				
				System.err.println("Got an exception!! in customer_table ");
			    System.err.println(e.getMessage());
			    profile.setErrorMsg(e.getMessage());
			    return false;
			}
			
	}
	
 
	// method for inserting doctor	
	
	public static boolean insertDoctorData(Profile profile) throws Exception {
		  System.out.println(profile);
		  try{
		    con = getCon();
		    System.err.println("Im Inserting");
	   	   PreparedStatement pst = con.prepareStatement("INSERT INTO doctor_table  VALUES (?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, profile.getMemberId());
			pst.setString(2, profile.getUser());
			pst.setString(3,profile.getFirstName());
			pst.setString(4,profile.getLastName());
			pst.setString(5,profile.getSpecialty());
			pst.setDouble(6,profile.getPhone());
			pst.setInt(7, profile.getZipCode());
			pst.setString(8,profile.getEmail());
			pst.setString(9,profile.getPassword());
			
			int result = pst.executeUpdate();
			pst.close();
			con.close();
			return (result > 0)? true : false; 
			}catch (SQLException e) {				
				System.err.println("Got an exception!! in doctor_table ");
			    System.err.println(e.getMessage());
			    profile.setErrorMsg(e.getMessage());
			    return false;
			    
			}
	}
	

	   // method for fetching specialty list
	    
		public static List<String> getSpecialty() throws Exception {
			  
			    List<String> specialtyList = new ArrayList<>();	
			    try{
			    con = getCon();
			    PreparedStatement pst = con.prepareStatement("SELECT Specialty FROM specialty");				
			    ResultSet rs = pst.executeQuery();
			    while(rs.next()){
			    	
			    	specialtyList.add(rs.getString("Specialty"));
			    				    	
			    }
			    
			    /*System.out.println("Hello I have got the value");
			    
			    System.out.println(specialtyList.size());*/
			    
			    if(specialtyList.size() == 0){
			    	return null;
			    }else{
			    	rs.close();
			    	pst.close();
					con.close();
			    	return specialtyList;
			    }
			    
		        }catch (SQLException e) {				
					System.err.println("Got an exception!! in Specialty_table ");
				    System.err.println(e.getMessage());
				    return null;
				}
			    
           }
		
		
		// Method for getting the user Details from Health_db using Member_Id , Password
		
		
		public static Profile getUserProfile(Profile profile) throws Exception {
			
			try{
			    con = getCon();
			    PreparedStatement pst = con.prepareStatement("SELECT * FROM customer_table WHERE member_id = ? AND password = ?");	
			    pst.setInt(1, profile.getMemberId());
			    pst.setString(2 ,profile.getPassword());			    
			    ResultSet rs = pst.executeQuery();
			    while(rs.next()){
			    	
			    	profile.setMemberId(rs.getInt("member_id"));
			    	profile.setUser(rs.getString("role"));
			    	profile.setFirstName(rs.getString("first_name"));
			    	profile.setLastName(rs.getString("last_name"));
			    	profile.setAge(rs.getInt("age"));
			    	profile.setPhone(rs.getDouble("phone"));
			    	profile.setHouseNumber(rs.getString("house_number"));
			    	profile.setCity(rs.getString("city"));
			    	profile.setZipCode(rs.getInt("zip_code"));
			    	profile.setState(rs.getString("state"));
			    	profile.setEmail(rs.getString("email"));
			    	String password = EncryptAndDecryptPassword.decrypt(rs.getString("password"));
			    	profile.setPassword(password);
			    	
			    				    	
			    }
			    
			    /* rs.first() return boolean  false if no rows are returned or row is not found in Customer_table */		    
			   
			  if(!rs.first()) { 
				  // Checking the Doctor_table for the profile , matching for Member_id and password
				   try{
				    PreparedStatement st = con.prepareStatement("SELECT * FROM doctor_table WHERE member_id = ? AND password = ?");	
				    st.setInt(1, profile.getMemberId());
				    st.setString(2 ,profile.getPassword());			    
				    ResultSet rst = st.executeQuery();
				    while(rst.next()){
				    	
				    	profile.setMemberId(rst.getInt("member_id"));
				    	profile.setUser(rst.getString("role"));
				    	profile.setFirstName(rst.getString("first_name"));
				    	profile.setLastName(rst.getString("last_name"));
				    	profile.setSpecialty(rst.getString("specialty"));
				    	profile.setPhone(rst.getDouble("phone"));				    	
				    	profile.setZipCode(rst.getInt("zip_code"));
				    	profile.setEmail(rst.getString("email"));
				    	profile.setPassword(rst.getString("password"));		    				    	
				      }		
				    
				      if(rst.first()){
				    	 // return matched doctor_profile for the member_id  and password
				    	  rst.close();
				    	  st.close();
				    	  con.close();
				    	  return profile ;
				    	  
				      }else{
				    	  /* rst.first() return boolean  false if no rows are returned or row is not found in doctor_table */	
				    	  rst.close();
				    	  st.close();
				    	  con.close();
				    	  profile.setErrorMsg("Profile Does not Exist , Please Register yourself and Try Again!!");
				    	  return profile;	
				    	  
				      }
				   }catch (SQLException e) {		
			        	/*If any errors got generated from doctor_table it will be notified here*/
						System.err.println("Got an exception!! in Doctor_Table while fetching single row ");
					    System.err.println(e.getMessage());
					    profile.setErrorMsg(e.getMessage());
					    return profile;
					}			    	 
			    	
			  }else{ // return matched customer_profile for the member_id  and password
				  
				        pst.close();
		    	        rs.close();
		    	        con.close();
			    		return profile;
			    		
			    		}			    
		        }catch (SQLException e) {		
		        	/*If any errors got generated from customer_table it will be notified here*/
					System.err.println("Got an exception!! in Customer_Table while fetching single row ");
				    System.err.println(e.getMessage());
				    profile.setErrorMsg(e.getMessage());
				    return profile;
				}
			
		} 
		
		
		
		
}