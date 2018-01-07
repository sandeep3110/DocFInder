import { Component } from "@angular/core";
import { DoctorHomeService } from "../RESTFul_API_Service/Doctor.Home.service";
import { CustomerAuthGuard } from "../Customer/Customer_AuthGuard";
import { Router } from "@angular/router";
import { IPatientAppointments } from "./Patient-Appointments";
import { Observable } from "rxjs/Observable";

"use strict";

@Component({
  selector: 'Doctor-HomeView',
  templateUrl: './Doctor_Home_View.html',
  styleUrls: ['./Doctor_Home_View.css']
})

export class DoctorHomeView extends CustomerAuthGuard {

  constructor(private doctorHomeService: DoctorHomeService, private rout: Router) {
    super(rout);
    this.getAppointmentsForToday();
    this.getAllReviews();
    this.getPatientLabReports();
  }

  getAppointmentsForToday() {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };

    this.doctorHomeService.getAppointmentsForToday(entries)
      .subscribe(appointments => {
        this.patientAppointments = appointments;
        // this.patientAppointments = [];
      },
      error => {
        this.errorMessage = <any>error;
    //    window.alert(this.errorMessage);
      });
  };

  // getAppointmentsForCards(appointmentsArray: Array<any>) {
  //   console.log('Appointments array is ', appointmentsArray);
  //   this.appointmentsForCards = appointmentsArray ? appointmentsArray.sort((a: any, b: any) => {
  //     if (new Date(a.date) < new Date(b.date))
  //       return -1;
  //     if (new Date(a.date) > new Date(b.date))
  //       return 1;
  //     return 0;
  //   }) : null;
  // };

  getAllReviews() {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };

    this.doctorHomeService.getPatientReviews(entries)
      .subscribe(reviews => {
        this.patientReviews = reviews;
      },
      error => {
        this.errorMessage = <any>error;
      });
  }

  getPatientLabReports(): any {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };

    this.doctorHomeService.getPatientLabReports(entries)
      .subscribe(labReports => {
        console.log("This is resports", labReports);
        this.patientLabReports = labReports;
      },
      error => {
        this.patientLabReports = [];
        this.noLabReports = <any>error;
        console.log("error message for lab ", this.noLabReports);
      });
  }

  appointmentsCardTitle: string = "Upcoming Appointments";
  errorMessage: string;
  labResultsCardTitle: string = "Patient Lab Results";
  noAppointments: string = "No Appointments for Today";
  noReviews: string = "No Reviews to Display";
  noLabReports: string;
  patientAppointments = <any>[];
  patientReviews = <any>[];
  patientLabReports = <any>[];
  spaceDash: string = ' - '
  patientReviewsLoaded: boolean = false;
  reviewsCardTitle: string = "Checkout what your patients wrote about you - Reviews";

}
