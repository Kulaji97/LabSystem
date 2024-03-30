import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';

import { environment } from '@environments/environment';
import { User } from '@app/_models';
import { UserType } from '@app/_models/usertype';
import { Appointment } from '@app/_models/appointment';

@Injectable({ providedIn: 'root' })
export class AccountService {
    private userSubject: BehaviorSubject<User | null>;
    public user: Observable<User | null>;
    private appointmentSubject: BehaviorSubject<Appointment | null>;
    public appointment: Observable<Appointment | null>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
        this.user = this.userSubject.asObservable();
        this.appointmentSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('appointment')!));
        this.appointment = this.appointmentSubject.asObservable();
    }

    public get userValue() {
        return this.userSubject.value;
    }

    login(username: string, password: string) {
        const user : User =  { username: username, password: password };
        
        return this.http.post<User>(`http://localhost:8080/users/authenticate`, user)
        .pipe(map(user => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user));
            this.userSubject.next(user);
            alert(user.email);
            return user;
        }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/account/login']);
    }

    register(user: User) {
        //creating a patient
        return this.http.post<User>(`http://localhost:8080/patients/create`, user);
    }

    // updateUser(user: User) {
    //     return this.http.post(`http://localhost:8080/patients/all`, user);
    // }


    getAll() {
        return this.http.get<User[]>(`http://localhost:8080/patients/all`);
    }
    
    getById(id: string) {
        return this.http.get<User>(`http://localhost:8080/users/${id}`);
    }

    update(id: string, params: any) {
        return this.http.put<any>(`http://localhost:8080/users/${id}`, params)
            .pipe(map(x => {
                alert(x);
                // update stored user if the logged in user updated their own record
                if (id == this.userValue?.id) {
                    // update local storage
                    
                    const user = { ...this.userValue, ...params };
                    localStorage.setItem('user', JSON.stringify(user));

                    // publish updated user to subscribers
                    this.userSubject.next(user);
                }
                return x;
            }));
    }

    delete(id: string) {
        return this.http.delete<any>(`http://localhost:8080/users/${id}`)
            .pipe(map(x => {
                // auto logout if the logged in user deleted their own record
                if (id == this.userValue?.id) {
                    this.logout();
                }
                return x;
            }));
    }

    getAppointmentByPatientId(userId: string) {
        return this.http.get<Appointment[]>(`http://localhost:8080/user/appointment/${userId}`);
    }

    getAppointmentById(id: string) {
        return this.http.get<Appointment>(`http://localhost:8080/appointment/${id}`);
    }

    updateAppointment(id: string, params: any){
        return this.http.put<any>(`http://localhost:8080/appointment/${id}`, params);
    }
}