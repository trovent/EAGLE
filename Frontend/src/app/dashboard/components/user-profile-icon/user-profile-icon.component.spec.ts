import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileIconComponent } from './user-profile-icon.component';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { StoreModule } from '@ngrx/store';
import { reducers } from '../../../store/app.states';

describe('UserProfileIconComponent', () => {
  let component: UserProfileIconComponent;
  let fixture: ComponentFixture<UserProfileIconComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        PopoverModule.forRoot(),
        FontAwesomeModule,
        HttpClientModule,
        RouterTestingModule,
        StoreModule.forRoot(
          reducers, {}
        )
      ],
      declarations: [UserProfileIconComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
