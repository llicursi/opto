import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {LoginRegisterComponent} from './login-register/login-register.component';

const routes: Routes = [
/*
  {path: '', component: Error404Component, pathMatch: 'full', canActivate: [RuleRouteGuard] },
*/

  {path: 'login', component: LoginComponent},
  {path: 'register', component: LoginRegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
