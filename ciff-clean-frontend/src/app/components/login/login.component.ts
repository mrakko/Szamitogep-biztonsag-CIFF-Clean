import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageUtil} from "../../util/validators";

@Component({
  selector: 'ciff-clean-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });
  hidePassword = true;

  constructor() {
  }

  get emailControl(): FormControl{
    return <FormControl<any>>this.loginForm.get('email');
  }

  get passwordControl(): FormControl{
    return <FormControl<any>>this.loginForm.get('password');
  }

  getErrorMessage(control: FormControl): string {
    return getErrorMessageUtil(control);
  }

  login(): void {
    // TODO: save token and userDTO to StorageService
  }
}
