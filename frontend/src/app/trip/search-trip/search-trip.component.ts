import {Component, OnInit} from '@angular/core';
import * as moment from 'moment';
import {Router} from '@angular/router';

@Component({
  selector: 'app-search-trip',
  templateUrl: './search-trip.component.html',
  styleUrls: ['./search-trip.component.css']
})
export class SearchTripComponent implements OnInit {
  private departure: string;
  private arrival: string;
  private date: Date;
  private time: string;
  constructor(private router: Router) { }

  ngOnInit() {
  }
  search() {
    console.log(JSON.stringify({
      'departure': this.departure,
      'arrival': this.arrival,
      'date': `${moment(this.date).format('YYYY-MM-DD')}T${this.time}:00`
    }));
    this.router.navigate(['/list-trip'], {queryParams: {date: `${moment(this.date).format('YYYY-MM-DD')}T${this.time}:00`,
      fromCountry: 'Russia', fromCity: this.departure, toCountry: 'Russia', toCity: this.arrival}});
    //tut vizvat service and navigate to table with trips
  }
}
