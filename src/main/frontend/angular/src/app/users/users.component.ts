import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../models/user';
import {MatDialog, MatTable} from '@angular/material';
import {UserService} from '../services/users.service';
import {AlertService} from '../services/alerts.service';
import {UsersModalComponent} from '../users-modal/users-modal.component';
import {Authorization} from '../models/authorization';
import {AuthenticationService} from '../services/authentication.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  displayedColumns: string[] = ['name', 'surname', 'email', 'role', 'star'];
  data: User[] = [];
  isLoadingResults = true;

  @ViewChild(MatTable, {static: true}) matTable: MatTable<any>;

  constructor(private usersService: UserService,
              private authService: AuthenticationService,
              private dialog: MatDialog,
              private alertService: AlertService) {
  }

  ngOnInit() {

    this.usersService.getAll()
      .subscribe(res => {
        this.data = res;
        this.refreshDataDisplay();
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });

  }

  /**
   * Creates the new user on a display dialog
   */
  createNewUser() {

    const element = new User();
    element.id = -1;
    element.name = '';
    element.surname = '';
    element.email = '';
    element.password = '123';
    element.role = 'USER';

    const modalCreate = this.dialog.open(UsersModalComponent, {
      data: {
        title: 'New user',
        user: element,
        hideDelete : true
      }
    });
    modalCreate.afterClosed().subscribe( dialogData => {
      if (!!dialogData && dialogData.name && dialogData.id) {
        this.saveUser(dialogData);
      }
    });
  }

  /**
   * Edits the current user on a Dialog display
   */
  editUser(selectedUser: User) {
    console.log(selectedUser);
    const modalUpdate = this.dialog.open(UsersModalComponent, {
      data: {
        title: 'Edit user',
        user: selectedUser,
        hideDelete : this.isSelf(selectedUser)
      }
    });
    modalUpdate.afterClosed().subscribe(dialogData => {
      if (!!dialogData && dialogData.name && dialogData.id) {
        this.updateUser(selectedUser, dialogData);
      } else if (!!dialogData && dialogData.delete) {
        this.deleteUser(selectedUser);
      }
    });
  }

  /**
   * Update the user on the list and persist
   */
  private updateUser(selectedUser: User, modifiedUser: User) {
    this.usersService.update(modifiedUser)
      .subscribe(
        data => {
          this.parseToElement(selectedUser, modifiedUser);
          this.refreshDataDisplay();
          // Call service
          this.alertService.snackSucess('User saved');
        },
        error => {
          console.error(error);
          this.alertService.snackError('Failed to update ' + selectedUser.name);
        });

  }

  /**
   * Delete the user from the list and persist
   */
  private deleteUser(selectedUser: User) {
    this.usersService.delete(selectedUser.id)
      .subscribe(
        data => {
          // Locate and destroy
          const index = this.data.indexOf(selectedUser, 0);
          if (index > -1) {
            this.data.splice(index, 1);
          }
          this.refreshDataDisplay();
          // Call service
          this.alertService.snackSucess('User ' + selectedUser.name + ' deleted');
        },
        error => {
          console.error(error);
          this.alertService.snackError(`Failed to delete user ${selectedUser.name} ${selectedUser.surname}`);
        });

  }

  /**
   * Creates a new user and adds it the data list
   */
  private saveUser(user: User) {
    // call service
    this.usersService.save(user)
      .subscribe(
        newUser => {
          this.data.push(newUser);
          this.refreshDataDisplay();
          this.alertService.snackSucess('User created');
        },
        error => {
          this.alertService.snackError('Failed to create ' + user.name);
        });

  }

  /**
   * Parse a pure json element into a User object referenced on the display
   * @param element Current user reference
   * @param result Json object of a user
   */
  private parseToElement(element: User, result: any) {
    element.name = result.name;
    element.surname = result.surname;
    element.email = result.email;
    element.password = result.password;
    element.role = result.role;
  }

  private refreshDataDisplay() {
    this.matTable.renderRows();
  }

  private isSelf(selectedUser: User) {
    return this.authService.currentUserValue.id === selectedUser.id;
  }
}
