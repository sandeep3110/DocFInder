import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
/* External Libraries */

import { RatingModule } from 'ng2-rating';
import { NKDatetimeModule } from 'ng2-datetime/ng2-datetime';

/* Compenents declaration */
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './PageNotFound/PageNotFound.component';
import { HeaderComponent } from './DefaultHome/Header.component';
import { FooterComponent } from './DefaultHome/Footer.component';
import { HomePageComponent } from './Home/Home.component';
import { LoginComponent } from './Login/Login.component';
import { PopUpBoxComponent } from './Login/PopUp.component';
import { CustomerSignUpComponent } from './Login/Customer.SignUp.component';
import { DoctorSignUpComponent } from './Login/Doctor.SignUp.component';
import { ContactusComponent } from './Home/ContactUs/contactus.component';

/* Customer-View*/

import { CustomerHeader } from './Customer/Customer_Header.component';
import { CustomerFooter } from './Customer/Customer_Footer';
import { CustomerDefaultView } from './Customer/Customer_Default_View';
import { CustomerHomeView } from './Customer/Customer_Home_View';
import { Appointment } from './Customer/Customer_appointment';
import { DoctorNearBy } from './Customer/Doctor_NearBy';
import { AppointmentModal } from './Customer/Customer_MakeAppointment';
import { ReviewModal } from './Customer/Add_Review';
import { DeleteAppoint } from './Customer/Delete_Appointment';

/* Doctor-View*/

import { DoctorHeader } from './Doctor/Doctor_Header.component';
import { DoctorFooter } from './Doctor/Doctor_Footer.component';
import { DoctorDefaultView } from './Doctor/Doctor_Defualt_View';
import { DoctorHomeView } from './Doctor/Doctor_Home_View.component';
import { DoctorAvailability } from './Doctor/Doctor_Availability.component';


/* Dependency Injection : Providers */

import { AuthenticationService } from './RESTFul_API_Service/Authentication.Service';
import { CustomerAuthGuard } from './Customer/Customer_AuthGuard';
import { CustomerService } from './RESTFul_API_Service/Customer.Home.service';
import { EmailNotification } from './RESTFul_API_Service/Email.Notifications.Service'
import { AppointmentService } from './RESTFul_API_Service/Appointment.service';
import { DoctorHomeService } from './RESTFul_API_Service/Doctor.Home.service';
import { UpdateDocAvailability } from './Doctor/Update_Availability.component';







"Use strict";

const appRoutes: Routes = [
  {
    path: '', component: AppComponent,
    children: [
      { path: '', component: HomePageComponent },
      { path: 'login', component: LoginComponent },
      { path: 'contactUs', component: ContactusComponent },

    ]
  },

  /* Customer-View*/

  {
    path: '', canActivate: [CustomerAuthGuard], component: CustomerDefaultView,
    children: [
      // From Login.component.ts it will come to  router.navigate(['home'])--> CustomerHomeView --> CustomerAuthGuard = true
      { path: 'home/:', component: CustomerHomeView },
      { path: 'home/:id/appointment', component: Appointment },
      { path: 'home/:id/delete-appointment', component: DeleteAppoint },
      { path: 'home/:id/appointment/docAppoint', component: AppointmentModal },
      { path: 'home/:id/appointment/docAppoint/:docId/addReview', component: ReviewModal },
    ]
  },



  /* Doctor-View*/

  {
    path: '', canActivate: [CustomerAuthGuard], component: DoctorDefaultView,
    children: [
      // From Login.component.ts it will come to  router.navigate(['home'])--> CustomerHomeView --> CustomerAuthGuard = true
      { path: 'doctorHome/:', component: DoctorHomeView },
      { path: 'doctorHome/:id/availability', component: DoctorAvailability },
      { path: 'doctorHome/:id/showAvailability', component: UpdateDocAvailability }
    ]
  },

  { path: '**', component: PageNotFoundComponent },


];


@NgModule({
  imports: [BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    /* External Libraries */
    RatingModule,
    NKDatetimeModule,],

  declarations: [AppComponent,
    PageNotFoundComponent,
    HeaderComponent,
    FooterComponent,
    HomePageComponent,
    LoginComponent,
    PopUpBoxComponent,
    CustomerSignUpComponent,
    DoctorSignUpComponent,
    ContactusComponent,

    /*Customer-View*/

    CustomerHeader,
    CustomerFooter,
    CustomerDefaultView,
    CustomerHomeView,
    Appointment,
    DoctorNearBy,
    AppointmentModal,
    ReviewModal,
    DeleteAppoint,

    /*Doctor-View*/

    DoctorHeader,
    DoctorFooter,
    DoctorDefaultView,
    DoctorHomeView,
    DoctorAvailability,
    UpdateDocAvailability],

  providers: [CustomerAuthGuard,
    AuthenticationService,
    CustomerService,
    EmailNotification,
    AppointmentService,
    DoctorHomeService],

  bootstrap: [AppComponent]
})
export class AppModule { }
