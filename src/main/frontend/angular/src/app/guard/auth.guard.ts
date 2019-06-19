import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (!this.isLoggedUser()) {
      // not logged in so redirect to login page with the return url
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    } else {
      const expectedRole = route.data.expectedRole;
      if (!!expectedRole) {
        const currentUser = this.authenticationService.currentUserValue;
        const role = currentUser.roles.find(userRole => userRole === expectedRole);
        if (!role) {
          this.router.navigate(['/404']);
          return false;
        }
      }
    }
    return true;
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
