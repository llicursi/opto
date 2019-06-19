import { BrowserModule } from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MaterialModule} from './material.module';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthGuard} from './guard/auth.guard';
import {LoginComponent} from './login/login.component';
// import {AlertsComponent} from './alerts/alerts.component';
// import {fakeBackendProvider} from './interceptors/fake-backend.js';
// import {ErrorInterceptor} from './interceptors/error.interceptor';
// import {JwtInterceptor} from './interceptors/jwt.interceptor';
import {HasRoleDirective} from './directives/has.role.directive';
import {AuthenticatedDirective} from './directives/authenticated.directive';
// import {Error404Component} from './pages/error404/error404.component';

@NgModule({
  exports: [
    HasRoleDirective
  ],
  declarations: [
    AppComponent,
  //  Error404Component,
    LoginComponent,
    AuthenticatedDirective,
    HasRoleDirective,
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
/*    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},*/
    AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
