import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import {Observable} from 'rxjs/Rx';

import 'rxjs/add/operator/map';

"use strict"
@Injectable()

export class AuthenticationService {

    constructor(private http : Http ){
        let headers = new Headers();
        headers.append('Content-Type','application/json');

    }
  
    /* All Https in Angular 2 protocols are asynchronous operations, Requesting server and      
     * getting thne response from server and handling the response
    /* to authenticate user and get his details */

    loginAuthentication(userData: any):any{
          
        return this.http.post("http://localhost:8080/ASP/HealthDB/myresource/authentication" , userData) /* Specifying Headers is optional */
        .map(
           (response:Response) => {
               
                 /* console.log(response);
                 if(response.status === 404) we are getting error for status code 404 not found on console that is the reason we are using catch block
                  return response.json(); */
                    sessionStorage.setItem("userData" , JSON.stringify(response.json()) );                 
                   /*  localStorage.setItem("userData" , JSON.stringify(response.json()) );    */
                    return response.json() ;
                  
            }                   
         )
         .catch (
            (error: any ) => {

              /*   console.log(error._body); displays only string holding JSON object.                    
                  Use the JavaScript function JSON.parse() to convert text/string into a TypeScript object:
                In the error we have the error message in 
                       _body{' "errorMsg" :"Profile doesn't not exist" '} --> string holding JSON object
                after JSON.parse it becomes 
                      [errorMsg : "Profile doesn't not exist"]  JSON object  
                      console.log(obj.errorMsg);   */
                
               var obj =  JSON.parse(error._body);               
               return (error.status === 404)? Observable.throw(obj.errorMsg) : null ;

                }            
            );

    }

}