import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StorageService } from 'src/app/services/authentication/storage.service';
import { MediaService } from 'src/app/services/networking';
import { DialogBoxAction } from '../dialog-box/dialog-box.component';
import { MediaModel } from '../file-list/file-list.component';

@Component({
  selector: 'ciff-clean-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  providers: [MediaService]
})
export class FileUploadComponent {

  fileToUpload: File | null = null;

  constructor(private mediaService: MediaService, public dialogRef: MatDialogRef<FileUploadComponent>, private snackBar: MatSnackBar, private storageService: StorageService) {}

  handleFileInput(event: Event) {
    let fileList = (event.target as HTMLInputElement).files;
    if (fileList)
      this.fileToUpload = fileList.item(0);
  }

  uploadFileToActivity() {
    const file = this.fileToUpload;
    if (file) {
      // TODO: show loader and disable confirm button
      file.arrayBuffer().then((arrayBuffer) => {
        const blob = new Blob([new Uint8Array(arrayBuffer)], {type: file.type });
        this.mediaService.uploadFileForm(blob).subscribe((fileId) => {
          this.snackBar.open("Uploaded successfully!", undefined, {
            duration: 2000,
          });
          const user = this.storageService.getUser();
          const uploadedModel: MediaModel = {
            id: fileId,
            fileName: file.name,
            uploaderName: user?.fullName ?? "",
            uploadDate: new Date(),
            numberOfComments: 0
          }
          this.dialogRef.close({
            event: DialogBoxAction.Confirm,
            data: uploadedModel
          });
          });
      });
    }
  }

  upload(){
    this.uploadFileToActivity();
  }

  closeDialog(){
    this.dialogRef.close({
      event: DialogBoxAction.Cancel
    });
  }

}
