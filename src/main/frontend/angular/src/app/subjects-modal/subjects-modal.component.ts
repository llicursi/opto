import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {
  DateAdapter,
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  MAT_DIALOG_DATA,
  MatDatepickerInputEvent,
  MatDialogRef
} from '@angular/material';
import {Subject} from '../models/subject';
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import * as moment from 'moment';


@Component({
  selector: 'app-subjects-modal',
  templateUrl: './subjects-modal.component.html',
  styleUrls: ['./subjects-modal.component.css'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-US'},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
  ]
})
export class SubjectsModalComponent implements OnInit {

  private static readonly DATE_FORMAT = 'YYYY-MM-DD';

  requestConfirmation = false;
  title = 'Edit subject';
  today: Date = new Date();
  dueDateFrom: Date = new Date();
  dueDateTo: Date = new Date();
  stateForm: FormGroup = this.fb.group({
    title: new FormControl({value: ''}, Validators.required),
    description: new FormControl({value: ''}),
    start: new FormControl({value: this.today}, Validators.required),
    due: new FormControl({value: this.today}, Validators.required),
    id: 0
  });

  constructor(public dialogRef: MatDialogRef<SubjectsModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.title = this.data.title;
    this.getSubjectDetails(this.data.subject);
    this.updateDueDateLimits(this.today);
  }

  /**
   * Receives the Subject and convert to a FormControl
   */
  getSubjectDetails(element: Subject) {
    this.stateForm.setValue({
      description: element.description,
      title: element.title,
      start: element.start,
      due: element.due,
      id: null
    });

  }
  /**
   * Triggers on Start date 'change' or 'input'
   */
  changeStartDate(type: string, event: MatDatepickerInputEvent<Date>) {
    this.updateDueDateLimits(event.value);
  }

  /**
   * Defines the rules of the due date, each time the start date
   * gets updated. Moves the due date to seven days after start
   * date or to thirty one days after start date.
   */
  private updateDueDateLimits(baseDate: Date) {
    let date = new Date(baseDate);
    date.setDate(date.getDate() + 7);
    this.dueDateFrom = date;

    date = new Date(baseDate);
    date.setDate(date.getDate() + 31);
    this.dueDateTo = date;

    const currentDueDate = this.stateForm.get('due').value;
    const firstDate = moment(baseDate);
    const secondDate = moment(currentDueDate);
    const diffInDays = (firstDate.diff(secondDate, 'days'));

    if (diffInDays > -7) {
      this.stateForm.patchValue({due: this.dueDateFrom});
      console.log(this.dueDateFrom);

    } else if (diffInDays < -31) {
      this.stateForm.patchValue({due: this.dueDateTo});
    }
    console.log('Diff in DAYS: ' + diffInDays);
  }


  /**
   * Confirm to save subject
   */
  saveSubject() {
    this.dialogRef.close(this.stateForm.value);
  }

  /**
   * Confirm to delete subject
   */
  confirmDelete() {
    this.dialogRef.close({delete : true});
  }

  /**
   * Just close the dialog
   */
  closeModal() {
    this.dialogRef.close(null);
  }

}
