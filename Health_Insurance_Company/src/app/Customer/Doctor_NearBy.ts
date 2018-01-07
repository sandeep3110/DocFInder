import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RatingModule } from 'ng2-rating';
import { CustomerAuthGuard } from './Customer_AuthGuard';
import { AppointmentService } from '../RESTFul_API_Service/Appointment.service';




"use strict";

@Component({
    selector: 'doctor-nearBy',
    templateUrl: './doctor_nearBy.html',
    styleUrls: ['./doctor_nearBy.css'],
})

export class DoctorNearBy extends CustomerAuthGuard {

    @Input('errMsg') message: string = null;
    @Input('elementList') docList = <any>[];
    @Input() carrier:any;
    
    
    
    

    constructor(private rout: Router , private apponit : AppointmentService) {
        super(rout);  
          }      

     
        setSelectedDoctorMemberId(id:any , name:any , option : string){
            
            /* console.log("Doctor Id" ,id)
            console.log("I'm in DoctorNearBy " ,this.customerData); */
            console.log(this.carrier);
            this.apponit.setDoctorAndPaitentDetails(id,name,this.carrier,this.customerData);
            if( option === 'Appointment')
            this.rout.navigate(['home/'+this.customerData.memberId+'/appointment/docAppoint'],{ queryParams: { doc_id: id} } );
            else
            this.rout.navigate(['home/'+this.customerData.memberId+'/appointment/docAppoint/'+id+'/addReview']);

          }
        
          


      
        
}

