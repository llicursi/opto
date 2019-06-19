import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SubjectService} from '../services/subject.service';
import {Subject} from '../models/subject';
import {MatDialog, MatTable} from '@angular/material';
import * as moment from 'moment';
import {AlertService} from '../services/alerts.service';
import {SettingsService} from '../services/settings.service';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {UserService} from '../services/users.service';
import {SubjectsFilterComponent} from '../subjects-filter/subjects-filter.component';
import {SubjectFilter} from '../models/subject-filter';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.css']
})
export class SubjectsComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['time', 'description', 'calories', 'star'];
  data: Subject[] = [];
  isLoadingResults = true;

  calPerDay = {};
  maxCalPerDay: number;

  userId: number = undefined;

  private subSettings: Subscription;
  private subRoute: Subscription;

  @ViewChild(MatTable) matTable: MatTable<any>;
  @ViewChild(SubjectsFilterComponent) filterSubjects: SubjectsFilterComponent;

  constructor(private subjectsService: SubjectService,
              private dialog: MatDialog,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private settingsService: SettingsService) {

  }

  ngOnInit() {

    // Initially loads the settings with max calories and observes for new changes
    this.settingsService.get();

    this.subSettings = this.settingsService.currentSettings.subscribe(settings => {
      if (settings.maxCalories) {
        const firstTime = (!this.maxCalPerDay);
        this.maxCalPerDay = settings.maxCalories;
        if (!firstTime) { this.matTable.renderRows(); }
      }
    });

    // Load the userId if provided on route
    this.subRoute = this.route.params.subscribe(params => {
      this.userId = params.id;
    });
    this.loadSubjectsList();

    // Monitor any change to the filters
    this.filterSubjects.currentFilter.subscribe(newFilter => {
      this.filter = newFilter;
    });

  }

  ngOnDestroy() {
    this.subSettings.unsubscribe();
    this.subRoute.unsubscribe();
  }

  private loadSubjectsList() {
    this.subjectsService.getSubjects(this.userId)
      .subscribe(res => {
        this.refreshDataDisplay(res);
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }

  /**
   * Creates the new subject on a display dialog
   */
  createNewSubject() {

    const element = new Subject();
    element.id = -1;
    element.description = '';
    element.calories = 100;
    element.date = moment(new Date()).format('YYYY-MM-DD');
    element.time = moment(new Date()).format('HH:mm');

    const modalCreate = this.dialog.open(SubjectsModalComponent, {
      data: {
        title: 'New subject',
        subject: element,
      }
    });
    modalCreate.afterClosed().subscribe(createdSubject => {
      if (!!createdSubject && createdSubject.description) {
        this.saveSubject(createdSubject);
      }
    });
  }

  /**
   * Edits the current subject on a Dialog display
   */
  editSubject(selectedSubject: Subject) {
    const modalUpdate = this.dialog.open(SubjectsModalComponent, {
      data: {
        title: 'Edit subject',
        subject: selectedSubject,
      }
    });
    modalUpdate.afterClosed().subscribe(modifiedSubject => {
      if (!!modifiedSubject && modifiedSubject.description) {
        this.updateSubject(selectedSubject, modifiedSubject);
      } else if (!!modifiedSubject && modifiedSubject.delete) {
        this.deleteSubject(selectedSubject);
      }
    });
  }

  /**
   * Update the subject on the list and persist
   */
  private updateSubject(selectedSubject: Subject, modifiedSubject: Subject) {
    this.subjectsService.updateSubject(modifiedSubject.id, modifiedSubject, this.userId)
      .subscribe(
        data => {
          this.parseToElement(selectedSubject, modifiedSubject);
          this.refreshDataDisplay(this.data);
          // Call service
          this.alertService.snackSucess('Subject saved');
        },
        error => {
          console.error(error);
          this.alertService.snackError('Failed to update ' + selectedSubject.description);
        });

  }

  /**
   * Update the subject on the list and persist
   */
  private deleteSubject(selectedSubject: Subject) {
    this.subjectsService.deleteSubject(selectedSubject.id, this.userId)
      .subscribe(
        data => {
          // Locate and destroy
          const index = this.data.indexOf(selectedSubject, 0);
          if (index > -1) {
            this.data.splice(index, 1);
          }
          this.refreshDataDisplay(this.data);
          // Call service
          this.alertService.snackSucess('Subject delete');
        },
        error => {
          console.error(error);
          this.alertService.snackError('Failed to delete ' + selectedSubject.description);
        });

  }

  /**
   * Creates a new subject and adds it the data list
   */
  private saveSubject(subject: Subject) {
    // call service
    this.subjectsService.addSubject(subject, this.userId)
      .subscribe(
        newSubject => {
          this.data.push(newSubject);
          this.refreshDataDisplay(this.data);

          this.alertService.snackSucess('Subject created');
        },
        error => {
          this.alertService.snackError('Failed to create ' + subject.description);
        });

  }

  /**
   * Parse a pure json element into a Subject object referenced on the display
   * @param element Current subject reference
   * @param result Json object of a subject
   */
  private parseToElement(element: Subject, result: any) {
    element.description = result.description;
    element.calories = result.calories;
    element.time = result.time;
    element.date = result.date;
  }

  /**
   * Sort displayed subjects list in decreasing order of date time.
   * Newest subject should appear on top.
   * @param res List of subjects
   */
  private sortData(res: Subject[]) {
    this.data = res.sort((a: any, b: any) => new Date(b.date + ' ' + b.time).getTime() - new Date(a.date + ' ' + a.time).getTime());
  }

  private refreshDataDisplay(res: Subject[]) {
    this.sortData(res);

    this.calPerDay = res.reduce((acc, el) => {
      acc[el.date] = (!!acc[el.date])
        ? acc[el.date] + (el.calories)
        : (el.calories);
      return acc;
    }, {});

    this.matTable.renderRows();
  }
}
