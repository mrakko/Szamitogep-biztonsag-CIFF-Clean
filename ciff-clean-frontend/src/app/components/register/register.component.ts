import { Component } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";

@Component({
  selector: 'ciff-clean-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(12), this.strongPass]),
    confirm: new FormControl('', [Validators.required]),
    fullName: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required])
  }, this.matchPassword);
  hidePassword = true;
  hideConfirm = true;

  get emailControl(): FormControl{ return <FormControl<any>>this.registerForm.get('email'); }
  get passwordControl(): FormControl{ return <FormControl<any>>this.registerForm.get('password'); }
  get confirmControl(): FormControl{ return <FormControl<any>>this.registerForm.get('confirm'); }
  get fullNameControl(): FormControl{ return <FormControl<any>>this.registerForm.get('fullName'); }
  get addressControl(): FormControl{ return <FormControl<any>>this.registerForm.get('address'); }

  getErrorMessage(control: FormControl): string {
    if (control.hasError('required')) {
      return 'You must enter a value';
    }
    if (control.hasError('strong')) {
      return 'At least one number, one lowercase and one uppercase letter required';
    }
    if (control.hasError('minlength')) {
      return `Minimum ${control.getError('minlength').requiredLength} character is required`;
    }

    return control.hasError('email') ? 'Not a valid email' : '';
  }

  matchPassword(control: AbstractControl): ValidationErrors | null {
    const password = control.get("password")?.value;
    const confirm = control.get("confirm")?.value;

    if (password != confirm) {
      return { 'noMatch': true }; }

    return null;
  }

  strongPass(control: FormControl): ValidationErrors | null  {
    let hasNumber = /\d/.test(control.value);
    let hasUpper = /[A-Z]/.test(control.value);
    let hasLower = /[a-z]/.test(control.value);
    // console.log('Num, Upp, Low', hasNumber, hasUpper, hasLower);
    const valid = hasNumber && hasUpper && hasLower;
    if (!valid) {
      // return what´s not valid
      return { strong: true };
    }
    return null;
  }

  register(): void {
    // TODO: save token and userDTO to TokenStorageService
  }
}
