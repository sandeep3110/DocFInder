
import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {CustomerAuthGuard} from './Customer_AuthGuard';
import { DoctorNearBy } from './Doctor_NearBy';



"use strict";

@Component({
    selector : 'Customer-HomeView',
    templateUrl: './Customer_Home_View.html',
    styleUrls : ['./Customer_Home_View.css'],
    
    
  })

export class CustomerHomeView extends CustomerAuthGuard{

  docNearByObject : any;
    
   
   /* Taking the sessionstorage into Customer values from Customer_AuthGuard.ts rather declaring another variable */
   constructor(private rout: Router) {
    super(rout);  
      }  
  

    
}