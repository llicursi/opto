import { Component } from '@angular/core';
import {AuthenticationService} from './services/authentication.service';
import {ActivationEnd, Router} from '@angular/router';
import {Authorization} from './models/authorization';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'opto-app';

  currentUser: Authorization;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    router.events.subscribe(event => {
      if (event instanceof ActivationEnd) {
        if (event.snapshot.data.title) {
          this.title = event.snapshot.data.title;
        } else {
          this.title = '';
        }
      }
    });
  }

  public logout() {
    this.authenticationService.logout();
    location.reload();
  }
}
