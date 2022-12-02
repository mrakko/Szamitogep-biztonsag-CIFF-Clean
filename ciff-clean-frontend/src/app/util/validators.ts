import {AbstractControl, FormControl, ValidationErrors} from "@angular/forms";

export function strongPass(control: FormControl): ValidationErrors | null  {
  let hasNumber = /\d/.test(control.value);
  let hasUpper = /[A-Z]/.test(control.value);
  let hasLower = /[a-z]/.test(control.value);

  const valid = hasNumber && hasUpper && hasLower;
  if (!valid) {
    return { strong: true };
  }
  return null;
}


export function matchPassword(control: AbstractControl): ValidationErrors | null {
  const password = control.get("password")?.value;
  const confirm = control.get("confirm")?.value;

  if (password != confirm) {
    return {'noMatch': true};
  }

  return null;
}

export function getErrorMessageUtil(control: FormControl): string {
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
