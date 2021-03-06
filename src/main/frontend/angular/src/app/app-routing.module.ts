import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {Error404Component} from './pages/error404/error404.component';
import {LoginRegisterComponent} from './login-register/login-register.component';
import {RuleRouteGuard} from './guard/rule.route.guard';
import {SubjectsComponent} from './subjects/subjects.component';
import {AuthGuard} from './guard/auth.guard';

const routes: Routes = [
  {path: '', component: Error404Component, pathMatch: 'full', canActivate: [RuleRouteGuard] },
  {path: 'subjects', component: SubjectsComponent, data: {title: 'Settings', validRoles : 'USER'}, canActivate: [AuthGuard]},
  {path: '404', component: Error404Component},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: LoginRegisterComponent},
  {path: '**',  redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
