import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {User} from '../models/user';
import {Roles} from '../guard/roles';
import {AlertService} from '../services/alerts.service';

@Component({
  selector: 'app-users-modal',
  templateUrl: './users-modal.component.html',
  styleUrls: ['./users-modal.component.css']
})
export class UsersModalComponent implements OnInit {
  hideDeleteButton = false;
  requestConfirmation = false;
  title = 'Edit user';
  stateForm: FormGroup = this.fb.group({
    name: new FormControl({value: ''}, Validators.required),
    email: new FormControl({value: ''}, Validators.required),
    password: new FormControl({value: '123'}, Validators.required),
    roleUser: new FormControl({value: false}),
    roleAdmin: new FormControl({value: false}),
    id: 0
  });

  constructor(public dialogRef: MatDialogRef<UsersModalComponent>,
              private alertService: AlertService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder) {
  }

  getUserDetails(element: User) {
    this.stateForm.setValue({
      name: element.name,
      email: element.email,
      password: element.password,
      id: element.id,
      roleUser: this.hasRole(element, Roles.USER.toString()),
      roleAdmin: this.hasRole(element, Roles.ADMIN.toString())
    });
  }

  ngOnInit() {
    this.title = this.data.title;
    this.getUserDetails(this.data.user);
    if (this.data.hideDelete) {
      this.hideDeleteButton = true;
    }
  }

  /**
   * Confirm to save user
   */
  saveUser() {
    // stop here if form is invalid
    if (this.stateForm.invalid) {
      return;
    }

    const userResponse = this.getUserFromForm();
    if (userResponse.role.length === 0) {
      this.alertService.snackError('Select at least one ROLE');
      return;
    }
    console.log(userResponse);
    this.dialogRef.close(userResponse);
  }

  private getUserFromForm(): User {
    const user = new User();
    user.id = this.stateForm.value.id;
    user.name = this.stateForm.value.name;
    user.email = this.stateForm.value.email;
    user.password = this.stateForm.value.password;
    user.role = this.anyRoleSelected(this.stateForm);
    return user;
  }

  /**
   * Confirm to delete user
   */
  confirmDelete() {
    this.dialogRef.close({delete: true});
  }

  /**
   * Just close the dialog
   */
  closeModal() {
    this.dialogRef.close(null);
  }

  /**
   * User has role
   */
  private hasRole(element: User, role: string) {
    return element.role.includes(role);
  }

  /**
   * Check if user has any ROLE selected and convert to string comma separated
   */
  private anyRoleSelected(stateForm: FormGroup): string {
    const rolesSelected = [];
    if (stateForm.value.roleUser) {
      rolesSelected.push(Roles.USER.toString());
    }
    if (stateForm.value.roleAdmin) {
      rolesSelected.push(Roles.ADMIN.toString());
    }

    return rolesSelected.join(',');
  }

}
