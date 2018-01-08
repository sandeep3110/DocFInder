import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, RequestMethod } from '@angular/http';


import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { IPatientAppointments } from '../Doctor/Patient-Appointments';
import { IDoctorProfile } from '../Doctor/Doctor-Profile';
import { IDoctorQualifications } from '../Doctor/Doctor-Qualifications';
import { IDoctorAvailability } from '../Doctor/Doctor_Availability';
import { IDoctorCredentials } from '../Doctor/Doctor_Credentials';

"use strict";

@Injectable()
export class DoctorHomeService {
  private _pastAppointmentsUrl = 'http://localhost:8080/ASP/HealthDB/doctor/pastAppointments';
  private _todayAppointmentsUrl = 'http://localhost:8080/ASP/HealthDB/doctor/todayAppointments';
  private _patientReviewsUrl = 'http://localhost:8080/ASP/HealthDB/doctor/reviews';
  private _patientLabReportsUrl = 'http://localhost:8080/ASP/HealthDB/doctor/labReports';
  private _getDoctorProfileUrl = 'http://localhost:8080/ASP/HealthDB/doctor/doctorProfile';
  private _updateDocProfileUrl = 'http://localhost:8080/ASP/HealthDB/doctor/updateProfile';
  private _addDocTimeSlots = 'http://localhost:8080/ASP/HealthDB/doctor/addTimeSlots';
  private _getDocTimeSlots = 'http://localhost:8080/ASP/HealthDB/doctor/getTimeSlots';
  private _deleteDocTimeSlot = 'http://localhost:8080/ASP/HealthDB/doctor/deleteTimeSlot';
  private _updatePassword = 'http://localhost:8080/ASP/HealthDB/doctor/updatePassword'
  private options:any;

  constructor(private http: Http) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
  }

  /* All Https in Angular 2 protocols are asynchronous operations, Requesting server and
 * getting thne response from server and handling the response
  /* to get the reason/speacilty/doctor_name */
  getAppointmentsForToday(entries: any): Observable<IPatientAppointments> {

    return this.http.post(this._todayAppointmentsUrl, entries) /* Specifying Headers is optional */
      .map(
      (response: Response) => {
        return response.json().appointmentsList;
      })
      .catch(this.handleError);
  }


  getPastAppointments(entries: any): Observable<IPatientAppointments> {

    return this.http.post(this._pastAppointmentsUrl, entries) /* Specifying Headers is optional */
      .map(
      (response: Response) => {
        return response.json().appointmentsList;
      })
      .catch(this.handleError);
  }

  getPatientReviews(entries: any): Observable<IPatientAppointments> {

    return this.http.post(this._patientReviewsUrl, entries) /* Specifying Headers is optional */
      .map(
      (response: Response) => {
        console.log("response is ", response.json());
        return response.json().patientReviewsList;
      })
      .catch(this.handleError);
  }

  getPatientLabReports(entries: any): Observable<IPatientAppointments> {
    return this.http.post(this._patientLabReportsUrl, entries) /* Specifying Headers is optional */
      .map(
      (response: Response) => {
        console.log("patientlab reports", response.json());
        return response.json().patientLabReports;
      })
      .catch(this.labReportsError);
  }

  getDoctorProfile(entries: any): Observable<IDoctorProfile> {
    return this.http.post(this._getDoctorProfileUrl, entries)
      .map(
      (response: Response) => {
        console.log("response is ", response.json());
        return response.json();
      })
      .catch(this.doctorProfileError);
  }

  updateDoctorProfile(data: any): Observable<IDoctorQualifications> {

    return this.http.put(this._updateDocProfileUrl, data)
      .map(
      (response: Response) => {
        console.log("updated response is ", response.json());
        return response.json();
      })
      .catch(this.updateDoctorProfileError);
  }

  addDocTimeSlots(entries: any): Observable<IDoctorAvailability> {
    console.log("Total schedule before post call", entries.doctorSchedule.length);
    return this.http.post(this._addDocTimeSlots, entries)
      .map(
      (response: Response) => {
        return response.json();
      })
      .catch(this.addDocTimeSlotsError);
  }

  getDocTimeSlots(entries: any): Observable<IDoctorAvailability> {
    return this.http.post(this._getDocTimeSlots, entries)
      .map(
      (response: Response) => {
        return response.json();
      })
      .catch(this.addDocTimeSlotsError);
  }

  deleteDocTimeSlot(entries: any): Observable<IDoctorAvailability> {
    console.log("Slot", entries.doctorSchedule);
    this.options = new RequestOptions({ 
      body: entries,
      method: RequestMethod.Delete
    });

    return this.http.request(this._deleteDocTimeSlot, this.options)
      .map(
      (response: Response) => {
        return response.json();
      })
      .catch(this.deleteDocTimeSlotsError);
  }

  updatePassword(entries: any): Observable<IDoctorCredentials> {
    return this.http.put(this._updatePassword, entries)
      .map(
      (response: Response) => {
        return response.json();
      })
      .catch(this.credentialsUpdateError);
  }

  private handleError(err: any) {
    console.log('this is error', err);
    return Observable.throw(JSON.parse(err._body).appointmentsList[0].errMessage);
  }

  private addDocTimeSlotsError(err: any): any {
    console.log('this is error', err);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }

  private getDocTimeSlotsError(err: any): any {
    console.log('this is error', err);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }

  private deleteDocTimeSlotsError(err: any): any {
    console.log('this is error', err);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }

  private doctorProfileError(err: any) {
    console.log('this is error', err);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }

  private labReportsError(err: any) {
    console.log('this is labreports error', JSON.parse(err._body).patientLabReports[0].errMessage);
    return Observable.throw(JSON.parse(err._body).patientLabReports[0].errMessage);
  }

  private updateDoctorProfileError(err: any) {
    console.log('this is update error', JSON.parse(err._body).errMessage);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }

  private credentialsUpdateError(err: any) {
    console.log('this is update error', JSON.parse(err._body).errMessage);
    return Observable.throw(JSON.parse(err._body).errMessage);
  }
}
