import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerAuthGuard } from './Customer_AuthGuard';


"use strict";

@Component({
  selector: 'Customer-Header',
  templateUrl: './Customer_Header.html',
  styleUrls: ['./Customer_Header.css'],


})
export class CustomerHeader extends CustomerAuthGuard {

  /* Either Local storage or session storage application is throughing error while 
    restarting the app --> "User Data doesn't exist on storage" */
  /* id:number = JSON.parse(sessionStorage.userData).memberId ; */ // Fetching the memberId for URL
  /* id:number = JSON.parse(localStorage.userData).memberId ; */

  id: number;

  /* Taking the sessionstorage into Customer values from Customer_AuthGuard_ts rather declaring another variable */
  constructor(private rout: Router) {
    super(rout);
    /* console.log(this.customerData.memberId); */
    this.id = this.customerData.memberId;
  }

  /* To make Log Out tab have a pointer cursor */
  pointer(): any {
    let myStyles = {

      'cursor': 'pointer',
    }
    return myStyles;
  }

  /* Log out from the session and clearing the storage */

  logOut() {
    sessionStorage.removeItem("customerData");
    window.sessionStorage.clear();
    location.reload(true);
    this.rout.navigate(['/login']);
  }

}