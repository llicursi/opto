import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
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
  dueDate: Date = new Date();
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

  getSubjectDetails(element: Subject) {
    this.stateForm.setValue({
      description: element.description,
      title: element.title,
      start: element.start,
      due: element.due,
      id: null
    });

  }

  ngOnInit() {
    this.title = this.data.title;
    this.getSubjectDetails(this.data.subject);
  }

  /**
   * Confirm to save subject
   */
  saveSubject() {
    this.stateForm.value.date = moment(this.stateForm.value.date).format(SubjectsModalComponent.DATE_FORMAT);
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
