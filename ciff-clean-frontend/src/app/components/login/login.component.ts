import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageUtil} from "../../util/validators";
import {AuthService, CreateUserDTO, LoginUserDTO, UserService} from "../../services/networking";
import {StorageService} from "../../services/authentication/storage.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {concat} from "rxjs";

@Component({
  selector: 'ciff-clean-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [AuthService, UserService]
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });
  hidePassword = true;
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private storageService: StorageService, private router: Router,
              private userService: UserService) {
  }

  get emailControl(): FormControl {
    return <FormControl<any>>this.loginForm.get('email');
  }

  get passwordControl(): FormControl {
    return <FormControl<any>>this.loginForm.get('password');
  }

  getErrorMessage(control: FormControl): string {
    return getErrorMessageUtil(control);
  }

  login(): void {
    const user: LoginUserDTO = {
      email: this.emailControl.value,
      password: this.passwordControl.value
    };

    this.authService.loginUser(user).subscribe((userToken) => {
      this.storageService.saveToken(userToken.token ?? "");
      this.userService.getUser().subscribe((user) => {
        this.storageService.saveUser(user);
        this.router.navigate(["file-list"]);
      })
    }, (error) => {
      if (error instanceof HttpErrorResponse && error.status == 500) {
        this.errorMessage = "Server is unreachable";
      }
      if (error instanceof HttpErrorResponse) {
        this.errorMessage = "Incorrect email or password";
      }
    })
  }
}
