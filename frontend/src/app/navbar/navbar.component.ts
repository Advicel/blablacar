import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {map} from 'rxjs/operators';
import { Observable } from 'rxjs/Observable';
import {error} from '@angular/compiler/src/util';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit() {
  }

  login() {
   this.router.navigateByUrl('login');
 }

  logout() {
    this.auth.logout();

 }

 home() {
    this.router.navigate(['/home']);
 }
 isLogged() {
   if (JSON.parse(localStorage.getItem('auth')) !== null && JSON.parse(localStorage.getItem('auth')) !== undefined) {
     return true;
   }
   return false;
 }
 addTrip() {
    this.router.navigate(['/add-trip']);
 }
 addCar(){
    this.router.navigate(['/add-car']);
 }
 searchTrip() {
    this.router.navigate(['/search']);
 }
 plans() {
    this.router.navigate(['/my-plans']);
 }
 myTrips() {
    this.router.navigate(['/my-trips']);
 }

}
