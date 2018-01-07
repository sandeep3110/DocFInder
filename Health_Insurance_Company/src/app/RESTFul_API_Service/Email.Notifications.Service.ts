import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';

"use strict";
@Injectable()

export class EmailNotification {

    constructor(private http : Http ){
        let headers = new Headers();
        headers.append('Content-Type','application/json');

    }

    /* All Https in Angular 2 protocols are asynchronous operations, Requesting server and 
     * getting thne response from server and handling the response
    /* API calling for Email subscription for User entered email in home page */
    newsLetterSubscription(emailEntry : any): any{
                     return this.http.post("http://localhost:8080/ASP/HealthDB/email/newsLetter",emailEntry)
                             .map(
                                 (response : Response) => {
                                     /* console.log(response); --> Response {_body: "{"age":0,"email":"prashanth6353@gmail.com","errorM…check your Inbox !....","memberId":0,"zipCode":0}" 
                                       Response itself is in String format*/
                                    /* console.log(response.json()); --> {age: 0, email: "prashanth6353@gmail.com", errorMsg: " Email with News letter has been Sent,Please check your Inbox !....", memberId: 0, zipCode: 0} */
                                                return response.json();
                                   }
                                 )
                            .catch(
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