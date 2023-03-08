import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartCandyDetailComponent } from './cart-candy-detail.component';

describe('CartCandyDetailComponent', () => {
  let component: CartCandyDetailComponent;
  let fixture: ComponentFixture<CartCandyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CartCandyDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CartCandyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
