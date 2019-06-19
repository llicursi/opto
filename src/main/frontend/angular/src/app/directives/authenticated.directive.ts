import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthenticationService} from '../services/authentication.service';

@Directive({
  selector: '[authenticated]'
})
export class AuthenticatedDirective implements OnInit {

  isVisible = false;

  /**
   * @param {ViewContainerRef} viewContainerRef
   *  -- the location where we need to render the templateRef
   * @param {TemplateRef<any>} templateRef
   *   -- the templateRef to be potentially rendered
   * @param {AuthenticationService} authenticationService
   *   -- will give us access to the roles a user has
   */
  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private authenticationService: AuthenticationService
  ) {
  }

  ngOnInit() {
    this.changeVisibility();
    this.authenticationService.currentUser.subscribe(user => {
      this.changeVisibility();
    })
  }

  /**
   * Toggle view visibility depending on the users role found
   */
  changeVisibility(){
    if (this.isAuthenticated()) {
      if (!this.isVisible) {
        this.isVisible = true;
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      }
    } else {
      this.isVisible = false;
      this.viewContainerRef.clear();
    }
  }

  /**
   * Check if user is authenticated
   */
  isAuthenticated() {
    return !!this.authenticationService.currentUserValue;
  }

}
