import { Component } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';

"use strict";

@Component({
  selector: 'Home-Page',
  templateUrl: './Home.html',
  styleUrls : ['./Home.css'],
  
})
export class HomePageComponent  { 

  images : any = ['./app/Images/Health-1.jpg', './app/Images/Health-2.jpg']; // Image path with respect to Angular execution i.e (from app folder) not with app.component.ts
  counter : number = 0;

       

        swapImages(): any{
           let myStyles = { 
           
             'background-image' : 'url(" '+this.images[this.counter]+' ")', 
             'background-size': 'cover', 
              
          }
          
          this.counter++;
          if (this.counter >= (this.images.length))   {this.counter = 0;}
          //setTimeout(this.swapImages,3000); 
          return myStyles;
        }

 }




