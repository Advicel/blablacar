import { Component, OnInit } from '@angular/core';
import {TripService} from '../services/trip.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Car} from '../data/Car';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  car: Car;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder,private tripService: TripService,private router: Router) {
    this.tripService = tripService;
  }
  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.thirdFormGroup = this._formBuilder.group({
      thirdCtrl: ['', Validators.required]
    });
  }

  addCar() {
    this.car = {
      color: this.secondFormGroup.getRawValue().secondCtrl,
      index: this.thirdFormGroup.getRawValue().thirdCtrl,
      brand: this.firstFormGroup.getRawValue().firstCtrl
    };
      this.tripService.addCar(this.car).subscribe(res=> {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
    this.router.navigateByUrl('my-trips');
  }

}
