import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppointmentViewComponent } from './view.component';
import { LayoutComponent } from './layout.component';
import { PaymentComponent } from './payment.component';
import { AddAppointmentComponent } from './add.component';
import { AppointmentsListComponent } from './appointments-list.components';


const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'view/:id', component: AppointmentViewComponent },
            { path: ':id/payment', component: PaymentComponent },
            { path: ':add/:id', component: AddAppointmentComponent },
            { path: '', component: AppointmentsListComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AppointmentsRoutingModule { }