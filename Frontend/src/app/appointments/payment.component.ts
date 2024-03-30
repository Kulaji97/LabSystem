import { Component, Input, OnInit } from '@angular/core';
import { User } from '@app/_models';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { RouterModule } from '@angular/router';

import { AccountService, AlertService } from '@app/_services';
import { Appointment } from '@app/_models/appointment';
import { DecimalPipe } from '@angular/common';

@Component({ templateUrl: 'payment.component.html' })
export class PaymentComponent implements OnInit{
    id?: string;
    user?:User;
    loading = false;
    appointment?:Appointment;
    
    
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService,
        private decimalPipe: DecimalPipe
    ) { }

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.loading = true;
        
        if (this.id) {
            this.accountService.getAppointmentById(this.id)
                .pipe(first())
                .subscribe(
                    (appointment: Appointment) => {
                        this.appointment = appointment;
                        const amount = appointment.amount == null ? 0.00 : parseFloat(appointment.amount);
                        this.appointment.amount = (this.decimalPipe.transform(amount, '1.2-2'))?.toString();
                        this.appointment.totalAmount = (this.decimalPipe.transform(((amount!=null ? amount : 0)  + 10.00), '1.2-2'))?.toString();
                    }
                );
        }
    
    }
    uploadTest() {
        // Logic to handle test upload
        console.log('Upload test button clicked');
    }
    
    payNow() {
        // Logic to handle payment
        console.log('Pay now button clicked');
        if(this.id) {
            const email = <HTMLInputElement>document.getElementById("payment_email");
            if (this.appointment) {
                this.appointment.email = email.value;
            }
            
            this.accountService.updateAppointment(this.id, this.appointment)
                .pipe(first())
                .subscribe(
                    (x: any) => {
                        this.alertService.success('Payment Added Successfully for the Appointment!', { keepAfterRouteChange: true });
                        this.router.navigateByUrl('/appointments/view/'+ this.id);
                    }
                );
        }
        
    }
}

    
