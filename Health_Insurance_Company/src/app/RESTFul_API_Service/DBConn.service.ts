import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import {Observable} from 'rxjs/Rx';

import 'rxjs/add/operator/map';

"use strict";
@Injectable()

export class DBConnection {

    constructor(private http : Http ){

    }

    /* All Https in Angular 2 protocols are asynchronous operations, Requesting server and 
     * getting thne response from server and handling the response
    /* To Insert data of Customer or Doctor */

    insertRegistartionValues(entries : any) : Observable<any[]>{

        /* let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Access-Control-Allow-Headers', 'Content-Type');
        headers.append('Access-Control-Allow-Methods', 'POST');
        headers.append('Access-Control-Allow-Origin', '*'); */
        let headers = new Headers();
        headers.append('Content-Type','application/json');
        console.log("hello",entries);
        return this.http.post("http://localhost:8080/ASP/HealthDB/myresource/single_user" , entries , {headers : headers} ) /* Specifying Headers is optional */
                       .map(
                              
                             /* console.log(response);
                                if(response.status === 404) we are getting error for status code 404 not found on console that is the reason for catch block
                                return response.json(); */

                          (response:any) => {
                                console.log(response);
                                /* console.log(response._body); */
                                 return response._body;
                           }                   
                        )
                        .catch(
                            (error: any) => {
                            if (error.status > 400) {
                              /*   return Observable.throw(new Error(error._body));   either of the ways works */
                              return Observable.throw(error._body);

                            }
                        }
                       );

                    }
                 

        /* To Get the Specialty list for the Doctor Form Field */

    getSpecialtyList() : any{

        let headers = new Headers();
        headers.append('Content-Type','application/json');
        return this.http.get("http://localhost:8080/ASP/HealthDB/myresource/specialty")
                        .map(
                               (response:Response) => {
                                   /* The json() method, when invoked, must return the result of running consume body with JSON. */
                                        return response.json();                                 
                                 }
                 );
           }


}