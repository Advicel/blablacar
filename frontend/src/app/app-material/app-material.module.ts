import {
  MatTabsModule,
  MatSidenavModule,
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatInputModule,
  MatCardModule,
  MatFormFieldModule,
  MatRadioModule,
  MatOptionModule,
  MatSelectModule,
  MatRippleModule,
  MatStepperModule,
  MatMenuModule, MatExpansionModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatChipsModule, MatDialogModule
} from '@angular/material';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {A11yModule} from '@angular/cdk/a11y';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatStepperModule,
    MatIconModule,
    MatListModule,
    DragDropModule,
    ScrollingModule,
    CdkTreeModule,
    CdkTableModule,
    A11yModule,
    MatInputModule,
    MatCardModule,
    MatFormFieldModule,
    MatRadioModule, MatOptionModule, MatSelectModule, MatRippleModule,
    MatMenuModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatChipsModule,
    MatDialogModule
    ],
  exports: [
    MatTabsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    DragDropModule,
    MatStepperModule,
    ScrollingModule,
    CdkTreeModule,
    CdkTableModule,
    A11yModule,
    MatInputModule,
    MatCardModule,
    MatFormFieldModule,
    MatRadioModule ,
    MatOptionModule, MatSelectModule, MatRippleModule,
    MatMenuModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatChipsModule,
    MatDialogModule
  ]
})
export class AppMaterialModule { }
