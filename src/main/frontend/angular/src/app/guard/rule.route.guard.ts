import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {Roles} from './roles';

@Injectable({
  providedIn: 'root'
})
export class RuleRouteGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (!this.isLoggedUser()) {
      // not logged in so redirect to login page with the return url
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    } else {
      const currentUser = this.authenticationService.currentUserValue;
      console.log(currentUser.roles);
      console.log(Roles.ADMIN.toString());
      if (currentUser.roles.includes(Roles.USER.toString())) {
        this.router.navigate(['/subjects']);
      } else if (currentUser.roles.includes(Roles.ADMIN.toString()) ) {
        this.router.navigate(['/users']);
      } /*else {
        //this.router.navigate(['/404']);
      }*/
    }
  }

  isLoggedUser() {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // authorised so return true
      return true;
    }
    return false;
  }

}
