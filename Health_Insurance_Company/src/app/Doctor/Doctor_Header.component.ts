import { Component } from "@angular/core";
import { CustomerAuthGuard } from "../Customer/Customer_AuthGuard";
import { Router } from "@angular/router";
import { DoctorHomeService } from "../RESTFul_API_Service/Doctor.Home.service";

@Component({
  selector: 'Doctor-Header',
  templateUrl: './Doctor_Header.html',
  styleUrls: ['./Doctor_Header.css']
})

export class DoctorHeader extends CustomerAuthGuard {

  /* Either Local storage or session storage application is throughing error while
     restarting the app --> "User Data doesn't exist on storage" */
  /* id:number = JSON.parse(sessionStorage.userData).memberId ; */ // Fetching the memberId for URL
  /* id:number = JSON.parse(localStorage.userData).memberId ; */

  id: number;
  modalTitle: string;
  profileModalTitle: string;
  patientAppointments = <any>[];
  patientReviews = <any>[];
  patientLabReports = <any>[];
  errorMessage: string;
  doctorProfile = <any>[];
  updateResponse = <any>[];
  editDocProfileMessage: string;
  hasMessage: boolean = false;

  /* Taking the sessionstorage into Customer values from Customer_AuthGuard_ts rather declaring another variable */
  constructor(private doctorHomeService: DoctorHomeService, private rout: Router) {
    super(rout);
    this.id = this.customerData.memberId;
  }

  showPastAppointmentsClicked(event: any) {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };

    this.modalTitle = "Past Appointments";

    this.doctorHomeService.getPastAppointments(entries)    
      .subscribe(appointments => {
        this.patientAppointments = appointments;
      },
      error => {
        this.errorMessage = <any>error;
      });
  };

  ShowReviewsClicked(event: any) {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };
    this.modalTitle = "Patient Reviews";
    this.doctorHomeService.getPatientReviews(entries)
      .subscribe(reviews => {
        this.patientReviews = reviews;
      },
      error => {        
        this.errorMessage = <any>error;
      });
  };

  ShowLabReportsClicked(event: any) {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };
    this.modalTitle = "Lab Reports For PickUp";
    this.doctorHomeService.getPatientLabReports(entries)
      .subscribe(labReports => {
        this.patientLabReports = labReports;
      },
      error => {
        
        this.errorMessage = <any>error;
      });
  }

  ShowAppointmentsForTodayClicked(event: any) {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };
    this.modalTitle = "More Appointments"
    this.doctorHomeService.getAppointmentsForToday(entries)
      .subscribe(appointments => {
        this.patientAppointments = appointments;
      },
      error => {        
        this.errorMessage = <any>error;
      });
  }

  DoctorProfileClicked(event: any) {
    var entries: any = {
      doctorMemberId: this.customerData.memberId
    };
    this.profileModalTitle = "Edit Profile"
    this.doctorHomeService.getDoctorProfile(entries)
      .subscribe(doctorProfile => {
        this.doctorProfile = doctorProfile;
      },
      error => {
        console.log("Get Doc Profile", this.doctorProfile);        
        this.editDocProfileMessage = <any>error;
        this.hasMessage = true;
      });
  }

  editDoctorProfile(event: any) {

    if (!this.doctorProfile.doctorMemberId) {
      this.doctorProfile.doctorMemberId = this.id;
      this.doctorProfile.profileExists = false;
    } else {
      this.doctorProfile.profileExists = true;
    }
    var entries = {
      affiliatedInsurance: this.doctorProfile.affiliatedInsurance,
      boardCertification: this.doctorProfile.boardCertification,
      doctorFirstName: this.doctorProfile.doctorFirstName,
      doctorLastName: this.doctorProfile.doctorLastName,
      doctorMemberId: this.doctorProfile.doctorMemberId,
      education: this.doctorProfile.education,
      hospitalAffliation: this.doctorProfile.hospitalAffliation,
      languagesSpoken: this.doctorProfile.languagesSpoken,
      professionalMemberships: this.doctorProfile.professionalMemberships,
      profileExists: this.doctorProfile.profileExists,
      specialities: this.doctorProfile.specialities
    };

    
    this.doctorHomeService.updateDoctorProfile(entries)
      .subscribe(doctorQualifications => {
        this.updateResponse = doctorQualifications;
        this.hasMessage = true;
        this.editDocProfileMessage = this.updateResponse.successMessage;
      },
      error => {
        this.editDocProfileMessage = <any>error;
        this.hasMessage = true;
      });
  }

  clearError() {
    this.editDocProfileMessage = "";
    this.hasMessage = false;
  }


  /* To make Log Out tab have a pointer cursor */
  pointer(): any {
    let myStyles = {

      'cursor': 'pointer',
    }
    return myStyles;
  }

  logOut() {
    sessionStorage.removeItem("customerData");
    //sessionStorage.removeItem("userData");
    window.sessionStorage.clear();
    location.reload(true);
    this.rout.navigate(['/login']);
  }

}
