import { Component, Inject, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogBoxData {
  id: number,
  dialogTitle: string,
  confirmButtonTitle: string,
  inputFieldText?: string
}

export enum DialogBoxAction {
  Cancel,
  Confirm
}

@Component({
  selector: 'ciff-clean-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.scss']
})
export class DialogBoxComponent {
  // data to pass back if needed (e.g. for dialog with input field)
  passBackData: any;
  confirmButtonTitle: string;
  dialogTitle: string;
  inputFieldText?: string;
  showInputField: boolean;

  constructor(
    public dialogRef: MatDialogRef<DialogBoxComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: DialogBoxData) {
    this.dialogTitle = data.dialogTitle;
    this.confirmButtonTitle = data.confirmButtonTitle;
    this.inputFieldText = data.inputFieldText;
    this.showInputField = !!data.inputFieldText
    this.passBackData = {...data};
  }

  doAction(){
    this.passBackData = {...this.passBackData, inputFieldText: this.inputFieldText };
    this.dialogRef.close({
      event: DialogBoxAction.Confirm,
      data: this.passBackData
    });
  }

  closeDialog(){
    this.dialogRef.close({
      event: DialogBoxAction.Cancel
    });
  }

}