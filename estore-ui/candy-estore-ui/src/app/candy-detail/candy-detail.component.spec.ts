import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandyDetailComponent } from './candy-detail.component';

describe('CandyDetailComponent', () => {
  let component: CandyDetailComponent;
  let fixture: ComponentFixture<CandyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandyDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CandyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
