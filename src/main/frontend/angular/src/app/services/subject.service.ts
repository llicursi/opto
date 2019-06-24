import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { Subject } from '../models/subject';
import {environment} from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
const endpoint = '/subject';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private http: HttpClient) { }

  getSubjects(userid?: number): Observable<Subject[]> {
    const resolvedRoute = this.resolveRoutePermission(userid);
    return this.http.get<Subject[]>(`${resolvedRoute}`)
      .pipe(
        tap(_ => console.log('fetched subject')),
        catchError(this.handleError('getSubjects', []))
      );
  }

  getSubject(id: number, userid?: number): Observable<Subject> {
    const resolvedRoute = this.resolveRoutePermission(userid);
    const url = `${resolvedRoute}/${id}`;
    return this.http.get<Subject>(url).pipe(
      tap(_ => console.log(`fetched subject id=${id}`)),
      catchError(this.handleError<Subject>(`getSubject id=${id}`))
    );
  }

  addSubject(subject, userid?: number): Observable<Subject> {
    const resolvedRoute = this.resolveRoutePermission(userid);
    return this.http.post<Subject>(`${resolvedRoute}`, subject, httpOptions).pipe(
      tap((s: Subject) => console.log(`added subject w/ id=${s.id}`)),
      catchError(this.handleError<Subject>('addSubject'))
    );
  }

  updateSubject(id, subject, userid?: number): Observable<any> {
    const resolvedRoute = this.resolveRoutePermission(userid);
    const url = `${resolvedRoute}/${id}`;
    return this.http.put(url, subject, httpOptions).pipe(
      tap(_ => console.log(`updated subject id=${id}`)),
      catchError(this.handleError<any>('updateSubject'))
    );
  }

  deleteSubject(id, userid?: number): Observable<Subject> {
    const resolvedRoute = this.resolveRoutePermission(userid);
    const url = `${resolvedRoute}/${id}`;
    return this.http.delete<Subject>(url, httpOptions).pipe(
      tap(_ => console.log(`deleted subject id=${id}`)),
      catchError(this.handleError<Subject>('deleteSubject'))
    );
  }

  voteSubject(id, agree: boolean): Observable<Subject> {
    const resolvedRoute = this.resolveRoutePermission();
    const url = `${resolvedRoute}/vote`;
    return this.http.put<Subject>(url, httpOptions).pipe(
      tap(_ => console.log(`vote subject id=${id}`)),
      catchError(this.handleError<Subject>('deleteSubject'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

  /**
   * Resolve the complete url based on the presence of the userid.
   *  {api}/subjects**
   *  {api}/users/{userid}/subjects**
   */
  private resolveRoutePermission(userid?: number) {

    if (userid === undefined) {
      return `${environment.apiUrl}${endpoint}`;
    }
    return `${environment.apiUrl}/user/${userid}${endpoint}`;

  }

}
