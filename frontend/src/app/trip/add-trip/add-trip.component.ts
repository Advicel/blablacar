import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Trip} from '../../data/Trip';
import * as moment from 'moment';
import {TripService} from '../../services/trip.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-trip',
  templateUrl: './add-trip.component.html',
  styleUrls: ['./add-trip.component.css']
})
export class AddTripComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  fifthFormGroup: FormGroup;
  trip: Trip;
  constructor(private _formBuilder: FormBuilder, private tripService: TripService, private router: Router) {}

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.thirdFormGroup = this._formBuilder.group({
      thirdCtrl: ['', Validators.required],
      datePickerCtrl: ['', Validators.required]
    });
    this.fourthFormGroup = this._formBuilder.group({
      fourthCtrl: ['', Validators.required]
    });
    this.fifthFormGroup = this._formBuilder.group({
      fifthCtrl: ['', Validators.required]
    });
  }

  create() {
    this.trip = {
      description: 'Description',
      arrival: {
        country: 'Russia',
        city: this.secondFormGroup.getRawValue().secondCtrl
      },
      departure: {
        country: 'Russia',
        city: this.firstFormGroup.getRawValue().firstCtrl
      },
      date: `${moment(this.thirdFormGroup.getRawValue().datePickerCtrl).format('YYYY-MM-DD')}T${this.thirdFormGroup.getRawValue().thirdCtrl}:00`,
      price: this.fifthFormGroup.getRawValue().fifthCtrl,
      countSeats: this.fourthFormGroup.getRawValue().fourthCtrl
    };
    this.tripService.addTrip(this.trip).subscribe(res => {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
    this.router.navigateByUrl('my-trips');
  }
}

