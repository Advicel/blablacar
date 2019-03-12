import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  phoneValidator = new FormControl('', [
    Validators.required,
    Validators.pattern('^$|[0-9]{10}'),
  ]);
  phoneValidator2 = new FormControl('', [
    Validators.required,
    Validators.pattern('^$|[0-9]{10}'),
  ]);
  loginData = {
    'login': null,
    'password': null
  };
  registrationData = {
    'login': null,
    'password': null
  };
  constructor(private authService: AuthService) {
    this.authService = authService;
  }

  ngOnInit() {
  }

  registration() {
    this.authService.registration(this.registrationData.login, this.registrationData.password);
  }
  login() {
    this.authService.login(this.loginData.login, this.loginData.password);
  }
}
