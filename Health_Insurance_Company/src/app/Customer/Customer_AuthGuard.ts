import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';


/* It is Mandatory to implement canActive method body in the AuthGuard Class because its an interface */
/* { Error: No provider for CustomerAuthGuard! } Dependency injection
To avoid the above error [Provider] should be specified in app.module.ts (or) Login.component.ts
*/
"use strict";

@Injectable()
export class CustomerAuthGuard implements CanActivate {

    customerData:any; 
    storage = <any>{}; 
    /* When TypeScript thinks that property "x" does not exist on "y", then you can always cast "y" into "any", 
    which will allow you to call anything (like "x") on "y". */

    constructor(private router: Router) {
        /* var userData:any = sessionStorage.userData;
        this.customerData = JSON.parse(userData); */
        this.storage = sessionStorage; 
        this.customerData = this.storage.userData ? JSON.parse(this.storage.userData) : false;
        /* console.log(" customer data ", this.customerData);    */     
        // this.customerData = storage.userData==null ? false: JSON.parse(storage.userData);
        // let sessionStorage = <any> {};
        // this.customerData = (sessionStorage).userData==null ? false: JSON.parse((sessionStorage).userData);
        /* this.customerData = JSON.parse(sessionStorage.userData);  */
     }

    /* Either Local storage or session storage application is throughing error while 
    restarting the app --> "User Data doesn't exist on storage" */

    canActivate() {

        
       if(this.customerData){ 
          /*  console.log(" customer data ", this.customerData); */
           return true;
          }
          /* if(localStorage.userData){ 
            return true;
           } */
          
          /* If No data is retrived or session / window is closed open from here */
              
                 this.router.navigate(['login']);
                 return false;
                 
          
    }

}