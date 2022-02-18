import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DmkComponent } from './dmk.component';

describe('DmkComponent', () => {
  let component: DmkComponent;
  let fixture: ComponentFixture<DmkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DmkComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DmkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
