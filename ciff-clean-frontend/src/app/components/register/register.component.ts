import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageUtil, matchPassword, strongPass} from "../../util/validators";
import {AuthService, CreateUserDTO} from "../../services/networking";
import {StorageService} from "../../services/authentication/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'ciff-clean-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  providers: [AuthService]
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

  constructor(private authService: AuthService, private storageService: StorageService, private router: Router) {
  }

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
    const user: CreateUserDTO = {
      address: this.addressControl.value,
      email: this.emailControl.value,
      fullName: this.fullNameControl.value,
      password: this.passwordControl.value
    };

    // TODO: save userDTO to StorageService
    this.authService.registerUser(user).subscribe((userToken) => {
        this.storageService.saveToken(userToken.value ?? "");
        this.router.navigate(["file-list"]);
      },
    );
  }

  getErrorMessage(control: FormControl): string {
    return getErrorMessageUtil(control);
  }
}
