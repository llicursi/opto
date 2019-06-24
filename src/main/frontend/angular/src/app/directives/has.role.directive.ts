import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthenticationService} from '../services/authentication.service';

@Directive({
  selector: '[hasRole]'
})
export class HasRoleDirective implements OnInit {
  // the role the user must have
  @Input('hasRole') roles: string[];
  isVisible = false;

  /**
   * @param viewContainerRef
   *  -- the location where we need to render the templateRef
   * @param templateRef
   *   -- the templateRef to be potentially rendered
   * @param authenticationService
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
      console.log('AUTH-CHANGE-VISIBILITY');
      this.changeVisibility();
    });
  }

  /**
   * Toggle view visibility depending on the users role found
   */
  changeVisibility() {
    if (this.hasRole()) {
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
   * Check if current user has all roles expected by the directive
   * @return whether the role was found or not
   */
  hasRole() {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser && currentUser.roles) {

      const expected = this.roles.length;
      let found = 0;
      Array.from(this.roles).forEach(role => {
        if (currentUser.roles.includes(role)) {
          found++;
        }
      });
      if (found === expected) {
        return true;
      }
    }
    return false;
  }

}
