import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { AccountService } from '@app/_services';

@Component({ templateUrl: 'appointments-list.component.html' })
export class AppointmentsListComponent implements OnInit {
    users?: any[];
    appointments?: any[];

    constructor(private accountService: AccountService) {}

    ngOnInit() {
        this.accountService.getAll()
            .pipe(first())
            .subscribe(users => this.users = users);

        this.accountService.getAllAppointments()
            .pipe(first())
            .subscribe(appointments => {
                this.appointments = appointments;
            });

        
    }
}