import { Component, Input, OnInit } from '@angular/core';
import { User } from '@app/_models';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { RouterModule } from '@angular/router';

import { AccountService, AlertService } from '@app/_services';
import { Appointment } from '@app/_models/appointment';
import { HttpClient } from '@angular/common/http';

@Component({ templateUrl: 'view.component.html' })
export class TestsViewComponent implements OnInit{
    id?: string;
    user?:User;
    loading = false;
    appointment?:Appointment;
    selectedFile: File | null = null;
    
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

    uploadFile() {
        if (this.selectedFile) {
          const formData = new FormData();
          formData.append('pdfFile', this.selectedFile);
          if (this.id) {
            formData.set("id", this.id);
          }
          
          this.http.post<any>('http://localhost:8080/tests/upload', formData).subscribe(response => {
            console.log('File uploaded successfully:', response.localFolderUrl);
          });
        }
      }
}

    
