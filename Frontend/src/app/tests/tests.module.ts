import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DecimalPipe } from '@angular/common';

import { AppointmentsRoutingModule } from './tests-routing.module';
import { TestsViewComponent } from './view.component';
import { LayoutComponent } from './layout.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AppointmentsRoutingModule
    ],
    declarations: [
        LayoutComponent,
        TestsViewComponent,
    ],
    providers: [DecimalPipe]
})
export class AppointmentsModule { }