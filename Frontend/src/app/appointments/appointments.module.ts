import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';

import { AppointmentsRoutingModule } from './appointments-routing.module';
import { AppointmentViewComponent } from './view.component';
import { LayoutComponent } from './layout.component';
import { AddAppointmentComponent } from './add.component';
import { AppointmentsListComponent } from './appointments-list.components';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AppointmentsRoutingModule
    ],
    declarations: [
        LayoutComponent,
        AppointmentViewComponent,
        AddAppointmentComponent,
        AppointmentsListComponent
    ],
    providers: [DecimalPipe]
})
export class AppointmentsModule { }