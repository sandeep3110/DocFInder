import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RatingModule } from 'ng2-rating';
import { CustomerAuthGuard } from './Customer_AuthGuard';
import { AppointmentService } from '../RESTFul_API_Service/Appointment.service';




"use strict";

@Component({
    selector: 'delete-appoint',
    templateUrl: './Delete_Appointment.html',
    styleUrls: ['./Delete_Appointment.css'],
})

export class DeleteAppoint extends CustomerAuthGuard {
   

    memberId : any ;
    patientAppointList = <any>{};
    appDate : any;

    constructor(private rout: Router , private appoint : AppointmentService) {
        super(rout);
        this.memberId = this.customerData.memberId;
        this.getPatientListOfAppointments();
      }

      getPatientListOfAppointments(): any {
        
        var entries = {
            memberId : this.memberId
        }
             
        this.appoint.listOfAppointments(entries)
        .subscribe(
            (result: any) => {
               //console.log("I'm in DeleteAppoint class",result.appointmentsList);
                     this.patientAppointList = result.appointmentsList;
               });
    }


    cancelAppoint(data : any):any{
        /* console.log(data.dateFromDb);
        console.log(new Date(data.dateFromDb));
        console.log(new Date(data.dateFromDb).getTime()); */
        this.appDate = new Date(data.dateFromDb).getTime();
          if(this.appDate > Date.now()){
              if( (this.appDate - Date.now()) > 8.64e+7 ){
                //console.log("Hello I'm in sending class")
                       var entries = {
                        memberId : this.memberId,
                        doctorMemberId : data.doctorMemberId
                       }
                       //console.log("Hello I'm in sending class",entries);
                        this.appoint.deleteAppointment(entries)
                             .subscribe(
                                 (result : any) => {
                                     console.log(result);
                                     window.alert(result.errMessage);
                                     this.rout.navigate(['home/'+this.memberId])
                                 }
                             );
                        
              }else{
                  var hours =  Math.floor((this.appDate - Date.now())/(1000*60*60));
                window.alert("You cannot cancel your appointment within 24 hours of appointment time,"+
                              "right now your have only "+ hours +
                              "  hours,Your Appointment cannot be cancelled");
                
              }

          }else{
              window.alert("You had already consulted the doctor,"+
                            "Your appointment history will be cancelled within 6 months");
          }
    }
    
}