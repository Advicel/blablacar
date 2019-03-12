import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  time = {hour: 13, minute: 30};
  constructor() { }

  ngOnInit() {
  }
  isLogged(): boolean {
    if (JSON.parse(localStorage.getItem('auth')) !== null && JSON.parse(localStorage.getItem('auth')) !== undefined) {
      return true;
    }
    return false;
  }

}
