import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileComponent } from './user-profile.component';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { Component } from '@angular/core';

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  @Component({ selector: 'e2nw-account-settings', template: '' })
  class AccountSettingsStubComponent { }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [TabsModule.forRoot()],
      declarations: [ UserProfileComponent, AccountSettingsStubComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});