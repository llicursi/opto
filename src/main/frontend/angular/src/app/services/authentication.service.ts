import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Authorization} from '../models/authorization';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<Authorization>;
  public currentUser: Observable<Authorization>;
  private basicAuth = btoa('web:10c25665e49274c39b8e8f7ad6e2a3d0b0bc5052');

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<Authorization>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): Authorization {
    return this.currentUserSubject.value;
  }

  /**
   * Authenticates a user via oauth2 and provides name and token
   * @param username User credential
   * @param password Password
   */
  public login(username: string, password: string) {

    const bodyPaylod = new URLSearchParams();
    bodyPaylod.set('grant_type', 'password');
    bodyPaylod.set('username', username);
    bodyPaylod.set('password', password);

    const headers = {
      Authorization: 'Basic ' + this.basicAuth,
      'Content-type': 'application/x-www-form-urlencoded'
    };

    return this.http.post<any>(
      `/oauth/token?grant_type=password`,
      bodyPaylod.toString(),
      {headers})
      .pipe(map(userAuth => {

        if (userAuth && userAuth.access_token) {
          localStorage.setItem('currentUser', JSON.stringify(userAuth));
          this.currentUserSubject.next(userAuth);
        }
        return userAuth;
      }));
  }

  /**
   * Removes current user from local data and notify
   */
  public logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
