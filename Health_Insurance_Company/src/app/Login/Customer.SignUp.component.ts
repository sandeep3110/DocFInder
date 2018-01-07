/* Angular Reactive forms api
"https://angular.io/guide/reactive-forms"

Form Group : FormGroup tracks the value and validity state of a group of AbstractControl instances. 
The group's properties include its child controls. The top-level form in your component is a FormGroup.

The FormBuilder class helps reduce repetition and clutter by handling details of control creation for you.

*/
import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import {DBConnection} from './../RESTFul_API_Service/DBConn.service';
import { Router } from '@angular/router';


"use strict";
@Component({
    selector : 'Cust-signUp-page',
    templateUrl: './Customer.SignUp.html',
    providers : [DBConnection],
    
  })
  export class CustomerSignUpComponent { 
  
    entryForm : FormGroup;
    value:any;
   
  
      constructor(private fb : FormBuilder , private dbConn : DBConnection , private router:Router ){
                    this.createForm();
                    this.value  = Math.floor(60000 + Math.random() * 10000) ; // to generate five digit member id greater than 60000
          }
  
          /* Validation for each and every form field */
          /*Test RegExp here - 'https://www.regexpal.com/'*/

          createForm(){
            this.entryForm = this.fb.group({
              firstname : ['',Validators.compose([Validators.required,Validators.pattern('[A-Za-z\\s]+')])], // Validation for First Name
              lastname : ['',Validators.compose([Validators.required,Validators.pattern('[A-Za-z\\s]+')])],  // Validation for Last Name             
              age : ['',Validators.compose([Validators.required,Validators.pattern('(\\d?[1-9]|[1-9]0)+')])], // validation for Age means first part - \d?(it should be [0-9]) and second digit[1-9] or second part - first digit [1-9] and second digit shoud be 0
              phone :['',Validators.compose([Validators.required,Validators.pattern('\\s*\\d{10}\\s*')])], // Validation for phone number 
              address : ['',Validators.compose([Validators.required,Validators.pattern('[\\w\\s]+')])],// Validation for Address
              city : ['',Validators.compose([Validators.required,Validators.pattern('[A-Za-z\\s]+')])],  // Validation for City
              zipcode : ['',Validators.compose([Validators.required,Validators.pattern('([1-9]{1}?\\d{4})+')])], // does not accept 0 at the beginning of the zip code
              state : ['',Validators.compose([Validators.required,Validators.pattern('([A-Za-z]{2})+')])], // Validation for state
              email : ['',Validators.compose([Validators.required,Validators.pattern('([\\w-\.]+@([\\w-]+\.)+[\\w-])+')])], // Valdatio for email
              password : ['',Validators.compose([Validators.required,Validators.pattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%])(?!.*\\s).{6,16})+")])],  // Validation for password
              ConfirmPassword : ['',Validators.compose([Validators.required,Validators.pattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%])(?!.*\\s).{6,16})+")])],  // Validation for Confirm password
               });      
               
               
               
             }

            
             
             

             register():any{

               var entries :any = {

                  /* Identifiers should match with the java model class Identifiers names */
                user : "customer",
                memberId : this.value,
                firstName : this.entryForm.get('firstname').value,
                lastName : this.entryForm.get('lastname').value ,
                age : this.entryForm.get('age').value,
                phone : this.entryForm.get('phone').value,
                houseNumber : this.entryForm.get('address').value,
                city : this.entryForm.get('city').value,
                zipCode : this.entryForm.get('zipcode').value,
                state : this.entryForm.get('state').value,
                email : this.entryForm.get('email').value,
                password : this.entryForm.get('password').value,              
                
                }
                
                this.dbConn.insertRegistartionValues(entries)
                  .subscribe(
                      (result:any)=>{
                              window.alert(result);
                              this.entryForm.reset();                             
                          },   
                                  
                          /* Error Handling from DBConn.service.ts */

                        (err: any) => {
                          window.alert(err);
                          this.entryForm.get("phone").reset(); // Error rises for Phone entry only beacuse Phone is Unique Key
                        });
              }
  
  
  }