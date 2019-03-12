import { Component, OnInit } from '@angular/core';
import {TripService} from '../../services/trip.service';
import {MatDialog} from '@angular/material';
import {DialogComponent} from '../my-trip/my-trip.component';

@Component({
  selector: 'app-my-plans',
  templateUrl: './my-plans.component.html',
  styleUrls: ['./my-plans.component.css']
})
export class MyPlansComponent implements OnInit {

  private dataSource;
  displayedColumns: string[] = ['description', 'from', 'to', 'date', 'price', 'delete'];
  constructor(private tripService: TripService) { }

  ngOnInit() {
    this.tripService.findMyPlans().subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    });
  }
  remove(id: string) {
    this.tripService.backJoin(id).subscribe(res => {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
  }

}
