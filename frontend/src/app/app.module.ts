import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppMaterialModule} from './app-material/app-material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import { AddTripComponent } from './trip/add-trip/add-trip.component';
import { SearchTripComponent } from './trip/search-trip/search-trip.component';
import { ListTripComponent } from './trip/list-trip/list-trip.component';
import { MyTripComponent } from './trip/my-trip/my-trip.component';
import { MyPlansComponent } from './trip/my-plans/my-plans.component';
import {DialogComponent} from './trip/my-trip/my-trip.component';
import { AddCarComponent } from './add-car/add-car.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    LoginComponent,
    AddTripComponent,
    SearchTripComponent,
    ListTripComponent,
    MyTripComponent,
    MyPlansComponent,
    DialogComponent,
    AddCarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppMaterialModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  entryComponents: [
    DialogComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
