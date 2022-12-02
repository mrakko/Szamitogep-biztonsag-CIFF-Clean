import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageUtil, matchPassword, strongPass} from "../../util/validators";

@Component({
  selector: 'ciff-clean-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(12), strongPass]),
    confirm: new FormControl('', [Validators.required]),
    fullName: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required])
  }, matchPassword);
  hidePassword = true;
  hideConfirm = true;

  get emailControl(): FormControl {
    return <FormControl<any>>this.registerForm.get('email');
  }

  get passwordControl(): FormControl {
    return <FormControl<any>>this.registerForm.get('password');
  }

  get confirmControl(): FormControl {
    return <FormControl<any>>this.registerForm.get('confirm');
  }

  get fullNameControl(): FormControl {
    return <FormControl<any>>this.registerForm.get('fullName');
  }

  get addressControl(): FormControl {
    return <FormControl<any>>this.registerForm.get('address');
  }

  register(): void {

  }

  getErrorMessage(control: FormControl): string {
    return getErrorMessageUtil(control);
  }
}
