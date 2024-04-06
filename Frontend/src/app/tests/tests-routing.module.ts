import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TestsViewComponent } from './view.component';
import { LayoutComponent } from './layout.component';


const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'view/:id', component: TestsViewComponent },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AppointmentsRoutingModule { }