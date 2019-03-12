import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TripService} from '../../services/trip.service';
import {Place} from '../../data/Place';
import {Trip} from '../../data/Trip';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-list-trip',
  templateUrl: './list-trip.component.html',
  styleUrls: ['./list-trip.component.css']
})
export class ListTripComponent implements OnInit {
  private dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['description', 'from', 'to', 'date', 'driver', 'price', 'join'];
  constructor(private activatedRoute: ActivatedRoute, private tripService: TripService) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      const date = params['date'];
      const fromCountry = params['fromCountry'];
      const fromCity = params['fromCity'];
      const toCountry = params['toCountry'];
      const toCity = params['toCity'];
      console.log(this.tripService.findTrips({
        country: fromCountry,
        city: fromCity
      }, {
        country: toCountry,
        city: toCity
      }, date).subscribe(data => {
        this.dataSource = data;
        console.log(this.dataSource);
      }));
    });
  }
  join(id: string) {
    this.tripService.join(id).subscribe(res => {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
  }

}
