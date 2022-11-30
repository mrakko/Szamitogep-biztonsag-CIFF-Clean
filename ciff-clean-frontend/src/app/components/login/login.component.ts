import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });
  hidePassword = true;

  constructor(private router: Router) {
  }

  get emailControl(): FormControl{
    return <FormControl<any>>this.loginForm.get('email');
  }

  get passwordControl(): FormControl{
    return <FormControl<any>>this.loginForm.get('password');
  }

  getErrorMessage(control: FormControl): string {
    if (control.hasError('required')) {
      return 'You must enter a value';
    }

    return control.hasError('email') ? 'Not a valid email' : '';
  }

  login(): void {

  }
}
