import { Component, Input, OnInit } from '@angular/core';
import { User } from '@app/_models';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { RouterModule } from '@angular/router';

import { AccountService, AlertService } from '@app/_services';
import { Appointment } from '@app/_models/appointment';
import { HttpClient } from '@angular/common/http';
import { Test } from '@app/_models/test';

@Component({ templateUrl: 'view.component.html' })
export class AppointmentViewComponent implements OnInit{
    id?: string;
    user?:User;
    loading = false;
    appointment?:Appointment;
    selectedFile: File | null = null;
    technicians?: User[];
    technician?: string;
    test?:Test;
    
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private alertService: AlertService,
        private http: HttpClient
    ) { }

    onFileSelected(event: any) {
      this.selectedFile = event.target.files[0];
    }

    ngOnInit() {
        this.id = this.route.snapshot.params['id'];
        this.loading = true;
        
        if (this.id) {
            this.accountService.getAppointmentById(this.id)
                .pipe(first())
                .subscribe(
                    (appointment: Appointment) => {
                        this.appointment = appointment;
                    }
                );

            this.accountService.getTestByAppointmentId(this.id).pipe(first())
                .subscribe((test: Test) => {
                  this.test = test;
                });
        }

        this.accountService.getAll()
            .pipe(first())
            .subscribe(users =>{
                this.technicians = users.filter(user => user.type == "3")
            }
        );
        
      }

    setTechnician(e:any) {
        const selectedTechnicianId = e.target.value;
        this.technician = selectedTechnicianId;
    }
       
    uploadFile() {
        if (this.selectedFile) {
          const formData = new FormData();
          formData.append('pdfFile', this.selectedFile);
          if (this.id) {
            formData.set("id", this.id);
          }

          if (this.appointment?.id) {
            formData.set("appointment_id", this.appointment.id);
          }

          if (this.technician) {
            formData.set("technician_id", this.technician);
          }

          this.http.post<any>('http://localhost:8080/tests/upload', formData).subscribe(response => {
            console.log('File uploaded successfully:', response);
            //this.router.navigateByUrl('/appointments/view/'+this.appointment?.id);
            this.ngOnInit();
            this.alertService.success('Test Created Successfully! File uploaded (Uploads/' + response + ')');
          });
        }
      }
}

    
