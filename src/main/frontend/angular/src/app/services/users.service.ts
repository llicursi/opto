import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {environment} from '../../environments/environment';
import {UserRegister} from '../models/user-register';
import {User} from '../models/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<User[]>(`${environment.apiUrl}/users`);
  }

  getById(id: number) {
    return this.http.get(`${environment.apiUrl}/users/${id}`);
  }

  register(user: UserRegister) {
    console.log('User to be created');
    console.log(user);
    return this.http.post(`${environment.apiUrl}/register`, user, {headers: {'content-type': 'application/json'}});
  }

  update(user: User) {
    return this.http.put(`${environment.apiUrl}/users/${user.id}`, user);
  }

  delete(id: number) {
    return this.http.delete(`${environment.apiUrl}/users/${id}`);
  }

  save(user: User) {
    return this.http.post<User>(`${environment.apiUrl}/users`, user, {headers: {'content-type': 'application/json'}});

  }
}
