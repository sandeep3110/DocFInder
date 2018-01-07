import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthenticationService} from './../RESTFul_API_Service/Authentication.Service';

"use strict";
@Component({
    selector : 'login-page',
    templateUrl: './Login.html',
    styleUrls : ['./Login.css'],
    
  })
  

  export class LoginComponent {

    loginForm : FormGroup;
   
    constructor(private fb : FormBuilder , private AuthService : AuthenticationService , private router:Router ){
                      
                this.loginFormValidation();
               }


         /* Login Form validation */

         loginFormValidation(){

          this.loginForm = this.fb.group({
            memberId : ['',Validators.required], // Validation for Member Id
            password : ['',Validators.required],  // Validation for Password 
         });

        }

        login() : any{

          var loginData : any = {
            /* Identifiers should match with the java model class Identifiers names */
            memberId : this.loginForm.get("memberId").value,
            password :this.loginForm.get("password").value,          
          }

              
          this.AuthService.loginAuthentication(loginData)
          .subscribe(

            (result : any) => {

                /* console.log(result); */
                (result.user === "doctor")? this.router.navigate(['doctorHome', result.memberId]) : this.router.navigate(['home', result.memberId]); 
                /* this.router.navigate(['home', result.memberId])      -->    It will look like this -->  http://localhost:3004/home/63236  
                this.router.navigate(['home'],{ queryParams: { id: result.memberId } } ); -->   It will look like this -->  http://localhost:3004/home/?id=63236   */
                    
                    /* var obj = JSON.parse(sessionStorage.userData);
                    console.log(obj); */
                    
            },

            (err: any) => {
              window.alert(err);
              this.loginForm.reset(); // Error rises for member Id and password because they doesn't exist in database or while subscribing from Authentication service
            }

          ); 
                   
        }
    
   }