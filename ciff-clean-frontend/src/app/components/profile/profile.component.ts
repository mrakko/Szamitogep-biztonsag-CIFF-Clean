import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageUtil, matchPassword, strongPass} from "../../util/validators";

@Component({
  selector: 'ciff-clean-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  profileForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    fullName: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required])
  });
  authForm = new FormGroup({
    oldPassword: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(12), strongPass]),
    confirm: new FormControl('', [Validators.required]),
  }, matchPassword);
  hideOld = true;
  hideNew = true;
  hideConfirm = true;

  get emailControl(): FormControl{ return <FormControl<any>>this.profileForm.get('email'); }
  get fullNameControl(): FormControl{ return <FormControl<any>>this.profileForm.get('fullName'); }
  get addressControl(): FormControl{ return <FormControl<any>>this.profileForm.get('address'); }

  get oldPasswordControl(): FormControl {
    return <FormControl<any>>this.authForm.get('oldPassword');
  }

  get passwordControl(): FormControl {
    return <FormControl<any>>this.authForm.get('password');
  }

  get confirmControl(): FormControl {
    return <FormControl<any>>this.authForm.get('confirm');
  }

  getErrorMessage(control: FormControl): string {
    return getErrorMessageUtil(control);
  }

  changeData(): void {

  }

  changePass(): void {

  }
}
