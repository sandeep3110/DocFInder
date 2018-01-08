import { Component } from "@angular/core";
import { CustomerAuthGuard } from "../Customer/Customer_AuthGuard";
import { DoctorHomeService } from "../RESTFul_API_Service/Doctor.Home.service";
import { Router } from "@angular/router";
import { timeInterval } from "rxjs/operator/timeInterval";

@Component({
    selector: 'Doctor-Availability',
    templateUrl: './Doctor_Availability.html',
    styleUrls: ['./Doctor_Availability.css']
})

export class DoctorAvailability extends CustomerAuthGuard {

    constructor(private doctorHomeService: DoctorHomeService, private rout: Router) {
        super(rout);
        this.getDocTimeSlots();

    }

    date: Date;
    submitCheckBox: boolean;
    currentDate: any;
    allDocSlots: any[];
    docTimeSlots: any[] = [];
    selectedDate: Date;
    start: Date = new Date();
    errorMessage: string;
    datepickerOpts = {     /* startDate: new Date(2016, 5, 10), */
        autoclose: true, todayBtn: 'linked',
        todayHighlight: true, assumeNearbyYear: true,
        enableOnReadonly: false,
        format: 'd MM yyyy', icon: 'fa fa-calendar', clearBtn: false,
        startDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 1), showOnFocus: true,
        endDate: new Date(new Date().getFullYear(), new Date().getMonth() + 3)
    };

    //If we remove type casting <any> it will throw error
    timepickerOpts: any[] = <any>{
        icon: 'fa fa-clock-o',
        showMeridian: false,
        minuteStep: 15,
        showSeconds: false,
        defaultTime: '08:00 AM'
    };

    getDate(dt: Date): number {
        this.date = dt;
        return dt && dt.getTime();
    }

    checkDuplicate(currentElement: Date): boolean {

        if (new Date(currentElement).valueOf() !== new Date(this.currentDate).valueOf()) {
            return true;
        } else {
            return false;
        }
    }

    addTimeSlot(date: Date) {
        this.selectedDate = new Date(date);
        console.log("this is after all get docl slots", this.allDocSlots);
        if (this.selectedDate.getHours() >= 8 && this.selectedDate.getHours() < 19) {
            var obj = { currentDate: new Date(this.selectedDate.setSeconds(0)).toISOString() };
            let isExist = (this.docTimeSlots.length < 1) ? true : this.docTimeSlots.every(this.checkDuplicate, obj);
            let isExistInDb = (this.allDocSlots.length < 1) ? isExist : this.allDocSlots.every(this.checkDuplicate, obj);

            if (isExist && isExistInDb) {
                this.docTimeSlots.push(this.selectedDate);
            } else if (!isExistInDb){
                window.alert("Looks like You have already added this time slot in the past, please add different time slot");
            } else if(!isExist) {
                window.alert("You have already added this time slot, please add different time slot");
            }
        } else {
            window.alert("Please select the time slot between 8:00 am and 7:00 pm");
        }
    }

    delete(slot: any) {
        this.docTimeSlots = this.docTimeSlots.filter(arrElement => arrElement.valueOf() != slot.valueOf());
    }

    getDocTimeSlots(): any {
        var docInfo: any = {
            doctorMemberId: this.customerData.memberId,
            doctorSchedule: []
        }

        this.doctorHomeService.getDocTimeSlots(docInfo)
            .subscribe(response => {
                this.allDocSlots = response.doctorSchedule;
                console.log("all doc slots length", this.allDocSlots);
            },
            error => {
                this.errorMessage = <any>error;
            });
    }

    submitTimeSlots() {
        if (this.docTimeSlots && this.docTimeSlots.length) {
            console.log(this.docTimeSlots);
            var entries: any = {
                doctorMemberId: this.customerData.memberId,
                doctorSchedule: this.docTimeSlots
            };

            this.doctorHomeService.addDocTimeSlots(entries)
                .subscribe(response => {
                    console.log(response);
                    window.alert(response.successMessage);
                    this.rout.navigate(['/doctorHome/' + this.customerData.memberId + '/showAvailability']);
                },
                error => {
                    this.errorMessage = <any>error;
                });

        } else {
            window.alert("Please add atleast one time slot");
        }
    }
}

