import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {getErrorMessageUtil, matchPassword, strongPass} from "../../util/validators";
import {
  AuthService,
  ChangeUserPasswordDTO,
  CreateUserDTO,
  EditUserDTO,
  UserDTO,
  UserRole, UserService
} from "../../services/networking";
import {catchError} from "rxjs/operators";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StorageService} from "../../services/authentication/storage.service";

@Component({
  selector: 'ciff-clean-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [AuthService, UserService]
})
export class ProfileComponent implements OnInit {
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
  errorMessage = "";
  errorMessageData = "";

  constructor(private authService: AuthService, private snackBar: MatSnackBar, private storageService: StorageService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    const user = this.storageService.getUser();
    this.emailControl.setValue(user?.email);
    this.fullNameControl.setValue(user?.fullName);
    this.addressControl.setValue(user?.address);
  }

  get emailControl(): FormControl {
    return <FormControl<any>>this.profileForm.get('email');
  }

  get fullNameControl(): FormControl {
    return <FormControl<any>>this.profileForm.get('fullName');
  }

  get addressControl(): FormControl {
    return <FormControl<any>>this.profileForm.get('address');
  }

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
    const user: EditUserDTO = {
      fullName: this.fullNameControl.value,
      email: this.emailControl.value,
      address: this.addressControl.value
    }

    this.userService.editUser(user).subscribe((newUser) => {
      this.errorMessageData = "";
        this.storageService.saveUser(newUser);
        this.snackBar.open('User edited successfully', undefined, {
          duration: 2000,
        });
      },(error) => {
        if (error instanceof HttpErrorResponse) {
          this.errorMessageData = "Unknown error";
        }
      }
    );
  }

  changePass(): void {
    const pass: ChangeUserPasswordDTO = {
      oldPassword: this.oldPasswordControl.value,
      newPassword: this.passwordControl.value
    };

    this.authService.changePassword(pass).subscribe((userToken) => {
      this.errorMessage = "";
        this.snackBar.open('Password changed successfully', undefined, {
          duration: 2000,
        });
      }, (error) => {
        if (error instanceof HttpErrorResponse) {
          this.errorMessage = "Incorrect old password";
        }
      }
    );
  }
}
