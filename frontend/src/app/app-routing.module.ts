import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent} from './login/login.component';
import { AddTripComponent} from './trip/add-trip/add-trip.component';
import {SearchTripComponent} from './trip/search-trip/search-trip.component';
import {MyTripComponent} from './trip/my-trip/my-trip.component';
import {ListTripComponent} from './trip/list-trip/list-trip.component';
import {MyPlansComponent} from './trip/my-plans/my-plans.component';
import {MainGuard} from './main.guard';
import {AddCarComponent} from './add-car/add-car.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent} ,
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'add-trip', component: AddTripComponent, canActivate: [MainGuard]},
  {path: 'search', component: SearchTripComponent},
  {path: 'my-trips', component: MyTripComponent, canActivate: [MainGuard]},
  {path: `list-trip`, component: ListTripComponent},
  {path: 'my-plans', component: MyPlansComponent, canActivate: [MainGuard]},
  {path:'add-car',component:AddCarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
