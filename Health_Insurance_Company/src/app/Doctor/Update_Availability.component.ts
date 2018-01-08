import { Component } from "@angular/core";
import { CustomerAuthGuard } from "../Customer/Customer_AuthGuard";
import { DoctorHomeService } from "../RESTFul_API_Service/Doctor.Home.service";
import { Router } from "@angular/router";


@Component({
    selector: 'Update-Availability',
    templateUrl: './Update_Availability.html',
    styleUrls: ['./Update_Availability.css']
})

export class UpdateDocAvailability extends CustomerAuthGuard {

    constructor(private doctorHomeService: DoctorHomeService, private rout: Router) {
        super(rout);
        this.getDocTimeSlots();
    }

    errorMessage: string;
    allDocSlots: any[];
    docTimeSlots: any[];

    delete(slot: any) {
        if (confirm("Are you sure you want to delete the selected availability ?")) {
            var entries: any = {
                doctorMemberId: this.customerData.memberId,
                doctorSchedule: []
            };

            entries.doctorSchedule.push(slot);
            this.doctorHomeService.deleteDocTimeSlot(entries)
                .subscribe(response => {
                    this.allDocSlots = this.getDocTimeSlots();
                    window.alert("selected availability has been deleted, patients scheduled for this appointment have been notified by email");
                },
                error => {
                    this.errorMessage = <any>error;
                });
        }
    }

    getDocTimeSlots(): any {
        var docInfo: any = {
            doctorMemberId: this.customerData.memberId,
            doctorSchedule: []
        }

        this.doctorHomeService.getDocTimeSlots(docInfo)
            .subscribe(response => {
                this.allDocSlots = response.doctorSchedule;
            },
            error => {
                this.errorMessage = <any>error;
            });
    }
}

