import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import{ EmailNotification } from './../RESTFul_API_Service/Email.Notifications.Service'

"use strict";

@Component({
  selector: 'Footer-home',
  templateUrl: './Footer.html',
  styleUrls : ['./Footer.css'],
  
})
export class FooterComponent  { 

  newsLetter : FormGroup;
  
    constructor(private fb : FormBuilder , private emailNotify : EmailNotification ){
       this.newsForm();
    }
    
    newsForm(){
      this.newsLetter = this.fb.group({
                            userEmail : ['',Validators.compose([Validators.required,Validators.pattern('([\\w-\.]+@([\\w-]+\.)+[\\w-])+')])], // Validation for email
                             });
    }

    newsLetterEmail(){

      /* Should be Json format email : "email_address" */

       var entry : any = {
              email : this.newsLetter.get('userEmail').value,
       }
         
       this.emailNotify.newsLetterSubscription(entry)
         .subscribe(
          (result: any) => {  
            console.log(result.errorMsg);
            window.alert(result.errorMsg);
            this.newsLetter.get("userEmail").reset();
          },
           
          /* Error Handling from DBConn.service.ts */
    
          (err: any) => {
            window.alert(err);
            this.newsLetter.get("userEmail").reset(); // Error rises for Phone entry only beacuse Phone number is Unique Key
          }

        );
         
    }

 }




