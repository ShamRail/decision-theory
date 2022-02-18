import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LvmComponent } from './lvm.component';

describe('LvmComponent', () => {
  let component: LvmComponent;
  let fixture: ComponentFixture<LvmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LvmComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LvmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
