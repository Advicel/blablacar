import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = 'http://localhost:8090/oauth/token';
  redirectUrl = null;
  constructor(private http: HttpClient, private router: Router) {
  }
  registration(username: string, password: string) {
    if (username !== '' && password !== '') {
      const body = new HttpParams()
        .set('username', username)
        .set('password', password);
      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/x-www-form-urlencoded');
      this.http.post('http://localhost:8090/user/create', body.toString(), {
        headers: headers
      }).subscribe((data: any) => {
        console.log(data);
        this.login(username, password);
      });
    }
  }
  login(username: string, password: string) {
    if (username !== '' && password !== '') {
      const body = new HttpParams()
        .set('username', username)
        .set('password', password)
        .set('grant_type', 'password');
      let headers = new HttpHeaders();
      headers = headers.append('Authorization', 'Basic ' + btoa('my-client:secret'));
      headers = headers.append('Content-Type', 'application/x-www-form-urlencoded');
      headers = headers.append('Accept', 'application/json');
      this.http.post(this.authUrl, body.toString(), {
        headers: headers
      }).subscribe((data: any) => {
        localStorage.setItem('auth', JSON.stringify(data));
        console.log(data);
        if (this.redirectUrl !== null && this.redirectUrl !== undefined) {
          this.router.navigate(this.redirectUrl);
        } else {
          this.router.navigate(['/home']);
        }
      });
    }
  }
  logout() {
    console.log(JSON.parse(localStorage.getItem('auth')));
    if (JSON.parse(localStorage.getItem('auth')) !== null && JSON.parse(localStorage.getItem('auth')) !== undefined) {
      const body = new HttpParams()
        .set('refresh_token', JSON.parse(localStorage.getItem('auth')).refresh_token);
      const headers = new HttpHeaders()
        .append('Authorization', `bearer ${JSON.parse(localStorage.getItem('auth')).access_token}`)
        .append('Content-Type', 'application/x-www-form-urlencoded');
      this.http.post(`http://localhost:8090/user/logout/`, body.toString(), {headers: headers}).subscribe(
        resp => {
          console.log(resp);
        }, err => {
          console.log(err);
        }
      );
      localStorage.removeItem('auth');
      this.router.navigateByUrl('/login');
    }
  }
  refresh() {
    if (JSON.parse(localStorage.getItem('auth')) !== null && JSON.parse(localStorage.getItem('auth')) !== undefined) {
      const body = new HttpParams()
        .set('refresh_token', JSON.parse(localStorage.getItem('auth')).refresh_token)
        .set('grant_type', 'refresh_token');
      let headers = new HttpHeaders();
      headers = headers.append('Authorization', 'Basic ' + btoa('my-client:secret'));
      headers = headers.append('Content-Type', 'application/x-www-form-urlencoded');
      headers = headers.append('Accept', 'application/json');
      return this.http.post(this.authUrl, body.toString(), {
        headers
      });
    }
  }
}
