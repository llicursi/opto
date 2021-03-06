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
      let validRoles = route.data.validRoles;
      if (!!validRoles) {

        if (!Array.isArray(validRoles)) {
          validRoles = [validRoles];
        }

        const currentUser = this.authenticationService.currentUserValue;
        let found = false;
        validRoles.forEach(role => {
          if (currentUser.roles.includes(role)) {
            found = true;
          }
        });

        return found;

      }
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
