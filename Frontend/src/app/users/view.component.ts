import { Component, Input, OnInit } from '@angular/core';
import { User } from '@app/_models';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { RouterModule } from '@angular/router';

import { AccountService, AlertService } from '@app/_services';
import { Appointment } from '@app/_models/appointment';

@Component({ templateUrl: 'view.component.html' })
export class ViewComponent implements OnInit{
    id?: string;
    user?:User;
    loading = false;
    appointments?:Appointment[];
    
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.loading = true;
        if (this.id)
        {
            this.accountService.getById(this.id)
                    .pipe(first())
                    .subscribe(
                        (user: User) => {
                          this.user = user;
                          this.user.id = this.id;
                          this.loading = false;
                        },
                        error => {
                          console.error('Error loading user:', error);
                          this.loading = false;
                        });
        }
        
        if (this.id) {
            this.accountService.getAppointmentByPatientId(this.id)
                .pipe(first())
                .subscribe(
                    (appointments: Appointment[]) => {
                        this.appointments = appointments;
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
    }
}

    
