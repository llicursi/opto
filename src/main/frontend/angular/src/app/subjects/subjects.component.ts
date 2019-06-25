import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SubjectService} from '../services/subject.service';
import {Subject} from '../models/subject';
import * as moment from 'moment';
import {MatDialog, MatTable} from '@angular/material';
import {AlertService} from '../services/alerts.service';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {SubjectsModalComponent} from '../subjects-modal/subjects-modal.component';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {Vote} from '../models/vote';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ])
  ]
})
export class SubjectsComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['title', 'votes', 'due', 'opts'];
  data: Subject[] = [];
  isLoadingResults = true;
  expandedElement: Subject | null;

  userId: number = undefined;

  private subSettings: Subscription;
  private subRoute: Subscription;

  @ViewChild(MatTable, {static: false}) matTable: MatTable<any>;

  constructor(private subjectsService: SubjectService,
              private dialog: MatDialog,
              private alertService: AlertService,
              private route: ActivatedRoute) {

  }

  ngOnInit() {

    // Load the userId if provided on route
    this.subRoute = this.route.params.subscribe(params => {
      this.userId = params.id;
    });
    this.loadSubjectsList();

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
   * Creates the new meal on a display dialog
   */
  createNewSubject() {

    const element = new Subject();
    element.description = '';
    element.title = '';
    element.start = (new Date()).toISOString();
    element.due = (new Date()).toISOString();

    const modalCreate = this.dialog.open(SubjectsModalComponent, {
      data: {
        title: 'New subject',
        subject: element,
      }
    });
    modalCreate.afterClosed().subscribe(createdSubject => {
      if (!!createdSubject && createdSubject.title) {
        this.saveSubject(createdSubject);
      }
    });
  }


  voteSubject(selectedSubject: Subject, event) {
    if (event) {
      event.preventDefault();
      event.stopPropagation();
    }
    const vote = new Vote();
    vote.agree = true;
    vote.subject = selectedSubject.id;
    this.subjectsService.voteSubject(vote) .subscribe(
      data => {
        data;
      }, error => {
        this.alertService.snackError(error.message);
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
          this.loadSubjectsList();
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
    element.title = result.title;
    element.start = result.start;
    element.user = result.user;
    element.due = result.due;
  }


  private refreshDataDisplay(res: Subject[]) {
    this.data = res;
    this.matTable.renderRows();
  }
}
