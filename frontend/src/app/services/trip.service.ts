import { Injectable } from '@angular/core';
import {Trip} from '../data/Trip';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Place} from '../data/Place';
import {catchError} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {error} from '@angular/compiler/src/util';
import {any} from 'codelyzer/util/function';
import {Car} from '../data/Car';

@Injectable({
  providedIn: 'root'
})
export class TripService {
  prefTripUrl = 'http://localhost:8090/trip/';
  prefAppointmentUrl = 'http://localhost:8090/appointment/';
  prefUserUrl = 'http://localhost:8090/user/';
  constructor(private http: HttpClient) {
  }
  findTrips(departure: Place, arrival: Place, date) {
    const url = `find?fromCountry=${departure.country}&fromCity=${departure.city}&toCountry=${arrival.country}&toCity=${arrival.city}&date=${date}`;
    return this.http.get(this.prefTripUrl + url).pipe(catchError(err => {
      console.log(err);
      return new Observable<any>();
    }));
  }
  removeTrip(id) {
    const url = `delete?id=${id}`;
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json');
    this.http.get(this.prefTripUrl + url, {headers: headers}).subscribe(res => {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
  }
  findMyPlans() {
    const url = `my-plans`;
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json');
    return this.http.get(this.prefTripUrl + url, {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable<any>();
    }));
  }
  findMyTrips() {
    const url = `my-trip`;
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json');
    return this.http.get(this.prefTripUrl + url, {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable<any>();
    }));
  }
  addTrip(trip: Trip) {
    const url = `add`;
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json')
      .append('Content-Type', 'application/json');
    return this.http.post(this.prefTripUrl + url, trip, {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable<any>();
    }));
  }
  join(id: string) {
    const url = `join`;
    const params = new HttpParams()
      .append('tripId', id);
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access'  , 'application/json')
      .append('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post(this.prefAppointmentUrl + url, params.toString(), {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable();
    }));
  }
  backJoin(appointmentId: string) {
    const url = `back-join`;
    const params = new HttpParams()
      .append('id', appointmentId);
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json')
      .append('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post(this.prefAppointmentUrl + url, params.toString(), {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable();
    }));
  }
  backConfirm(appointmentId: string) {
    const url = `back-confirm`;
    const params = new HttpParams()
      .append('id', appointmentId);
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json')
      .append('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post(this.prefAppointmentUrl + url, params.toString(), {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable();
    }));
  }
  addCar(car: Car) {
    const url = `add-car`;
    const headers = new HttpHeaders()
      .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
      .append('Access', 'application/json')
      .append('Content-Type', 'application/json');
    return this.http.post(this.prefUserUrl + url, car, {headers: headers}).pipe(catchError(err => {
      console.log(err);
      return new Observable();
    }));
  }
}
