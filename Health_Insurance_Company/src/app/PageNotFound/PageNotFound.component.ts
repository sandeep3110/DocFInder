import {Component} from '@angular/core';
import { Router } from '@angular/router';

"use strict";

@Component({
  selector: 'pageNotFound',
  templateUrl : './PageNotFound.html',
  styleUrls : ['./PageNotFound.css'],
})
export class  PageNotFoundComponent {

         constructor (private route : Router){}
          
         
           pointer(): any {
                   let myStyles = {
                                   'cursor': 'pointer'
                                  }
                   return myStyles;
                       }

          redirect(){

                  sessionStorage.removeItem("userData");
                  window.sessionStorage.clear();
                  location.reload(true);
                  this.route.navigate(['/login']);
                }
    
}