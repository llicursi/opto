import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MealsModalComponent } from './subjects-modal.component';

describe('MealsModalComponent', () => {
  let component: MealsModalComponent;
  let fixture: ComponentFixture<MealsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MealsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MealsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
