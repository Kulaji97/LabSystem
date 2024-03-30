import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';

import { AppointmentsRoutingModule } from './appointments-routing.module';
import { AppointmentViewComponent } from './view.component';
import { LayoutComponent } from './layout.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AppointmentsRoutingModule
    ],
    declarations: [
        LayoutComponent,
        AppointmentViewComponent,
    ],
    providers: [DecimalPipe]
})
export class AppointmentsModule { }