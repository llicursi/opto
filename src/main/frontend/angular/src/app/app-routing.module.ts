import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
/*import {AuthGuard} from './guard/auth.guard';*/
import {LoginComponent} from './login/login.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
 /* {path: '**',  redirectTo: '/404'}*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
