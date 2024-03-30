import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService, AlertService } from '@app/_services';
import { User } from '@app/_models/user';
import { Appointment } from '@app/_models/appointment';

@Component({ templateUrl: 'add.component.html' })
export class AddAppointmentComponent implements OnInit {
    form!: FormGroup;
    id?: string;
    title!: string;
    loading = false;
    submitting = false;
    submitted = false;
    user?:User;
    doctors?: User[];
    appointment?:Appointment;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];

        this.form = this.formBuilder.group({
            testtype: ['', Validators.required],
            doctor: ['', Validators.required],
            email: ['', Validators.required],
            amount: ['', Validators.required],
            date: ['', Validators.required]
        });

        if (this.id) {
            this.accountService.getById(this.id)
                .pipe(first())
                .subscribe(x => {
                    this.user = x;
                    this.loading = false;
                });
        }

        this.accountService.getAll()
            .pipe(first())
            .subscribe(users =>{
                this.doctors = users.filter(x => x.type = "2")
            }
        );
    }

    // convenience getter for easy access to form fields
    get f() { return this.form.controls; }

    onSubmit() {
        
        this.submitted = true;

        // reset alerts on submit
        this.alertService.clear();
        
        this.submitting = true;
        const val = this.form.value;

        const selectedDate = new Date(val.date);
        const formattedDate = selectedDate.toISOString().slice(0, -1);

        this.appointment = {
            doctorId: val.doctor,
            patientId: this.id,
            amount: "100.00",
            testTypeId: val.testtype,
            time: formattedDate
        };

        this.accountService.addAppointment(this.appointment)
                .pipe(first())
                .subscribe({
                    next: (x) => {
                        this.alertService.success('Appointment added', { keepAfterRouteChange: true });
                        this.router.navigateByUrl('/users');
                    },
                    error: error => {
                        this.alertService.error(error);
                        this.submitting = false;
                    }
                });

        // this.saveUser()
        //     .pipe(first())
        //     .subscribe({
        //         next: () => {
        //             this.alertService.success('User saved', { keepAfterRouteChange: true });
        //             this.router.navigateByUrl('/users');
        //         },
        //         error: error => {
        //             this.alertService.error(error);
        //             this.submitting = false;
        //         }
        //     })


    }
}