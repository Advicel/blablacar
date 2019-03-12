import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatTableDataSource} from '@angular/material';
import {Appointment} from '../../data/Appointment';
import {TripService} from '../../services/trip.service';
import {error} from '@angular/compiler/src/util';

@Component({
  selector: 'app-my-trip',
  templateUrl: './my-trip.component.html',
  styleUrls: ['./my-trip.component.css']
})
export class MyTripComponent implements OnInit {
  private dataSource;
  displayedColumns: string[] = ['description', 'from', 'to', 'date', 'price', 'passengers'];
  constructor(private tripService: TripService, private dialog: MatDialog) { }

  ngOnInit() {
    this.tripService.findMyTrips().subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    });
  }
  remove(passenger) {
    console.log(passenger);
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: passenger
    });
  }

}

@Component({
  selector: 'app-dialog',
  templateUrl: 'dialog.html',
})
export class DialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Appointment, private tripService: TripService) {}

  ngOnInit() {
    }

  close(): void {
    this.dialogRef.close();
  }
  remove(): void {
    this.tripService.backConfirm(this.data.id).subscribe(res => {
      console.log(res);
    }, error1 => {
      console.log(error1);
    });
    this.dialogRef.close();
  }

}
