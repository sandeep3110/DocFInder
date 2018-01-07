import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerHomeView } from './Customer_Home_View';
import { CustomerService } from './../RESTFul_API_Service/Customer.Home.service';
import { AppointmentService } from '../RESTFul_API_Service/Appointment.service';



"use strict";

@Component({
    selector : 'Customer-Appointment',
    templateUrl: './Customer_appointment.html',
    styleUrls : ['./Customer_appointment.css'],
  })

export class Appointment {

    pinState:{pin: number , state: string }[] =[ {pin:19341, state:"PA"}, {pin:78954, state:"PA"}, {pin:78960 , state:"PA"} , {pin:19300, state:"PA"},
                                                 {pin:64052, state:"MO"}, {pin:64785, state:"MO"}, {pin:64097 , state:"MO"} , {pin:64093, state:"MO"},  ];
    
    searchForm: FormGroup;
    reasonList :String[];
    doctorsList : any[];
    errorMessage : string;

    /* Reason to hard code the values but not fetching from database is Career names will be know to everyone */
    listOfcarriers : string[] = [ "First Health Insurance",
                                  "First Choice Health - PPO",
                                  "Cigna - HMO",
                                  "UnitedHealthcare - UnitedHealthcare Compass Plus" ,
                                  "Independence Blue Cross - National BlueCard PPO",
                                  "EmblemHealth - 9/11 Program",
                                  "Clover Health - Prestige",
                                  "Companion Life - Worker's Comp",
                                  "WEA Trust - Fox River Network: Tier 1 Providers",
                                  "Caterpillar - Caterpillar Network Plan",
                                  "Corvel - Group Health",
                                  "irst Health Insurance"];
                                 

    constructor(private fb: FormBuilder , private CustmService : CustomerService , private appointservice : AppointmentService){
        this.showForm();
        this.getListOfReasonsAndDoctors();
    }

    
    
     


    showForm(){
        this.searchForm = this.fb.group({

                  reason: ['', Validators.required], // Validation for reason
                  zipcode: ['', Validators.required],  // Validation for zipcode
                  carrier: ['', Validators.required],  // Validation for carrier

                });
    }

    getListOfReasonsAndDoctors() {
        this.CustmService.getReasonList()
        .subscribe(
            (result: any) => {
                /* console.log(result.reasonSet); */
                this.reasonList = result.reasonSet;
                /* console.log(this.reasonList); */
            }
        );
    }

    

    nearByDoctors(){
        var entries = {
            /* Same value but assigned to three difference keys is because we are searching database with either of 
            disease,specialty,doctorName and zipcode to get member_Id from appointment_doctors_list table 
            and then with  that member_Id we are seraching for data in doctor_availability_list */

            disease :  this.searchForm.get('reason').value,
            specialty : this.searchForm.get('reason').value,
            doctorName : this.searchForm.get('reason').value,
            zipcode : this.searchForm.get('zipcode').value,
        }
        
        this.appointservice.getDoctorsList(entries)
          .subscribe(
            (result:any)=>{
                console.log("I'm in Appointment class ",result);
                this.doctorsList = result;
                this.errorMessage = null;
                                            
            },
            (err: any) => {
                
                this.errorMessage = err;
                this.doctorsList = null;
                this.searchForm.reset(); // Error rises for member Id and password because they doesn't exist in database or while subscribing from Authentication service
              } 
          )
          
        
        
    }

}
