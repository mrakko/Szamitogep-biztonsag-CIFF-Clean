import {StorageService} from 'src/app/services/authentication/storage.service';
import {EditFileDTO, MediaDTO, MediaService, UserRole} from 'src/app/services/networking';
import {MatDialog} from '@angular/material/dialog';
import {DialogBoxAction, DialogBoxComponent, DialogBoxData} from '../dialog-box/dialog-box.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FileUploadComponent} from '../file-upload/file-upload.component';

export interface MediaModel {
  id: number;
  fileName: string;
  uploaderName: string;
  uploadDate: Date;
  numberOfComments: number;
}

export enum OpenDialogReason {
  Delete,
  Modify
}

@Component({
  selector: 'ciff-clean-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss'],
  providers: [MediaService]
})

export class FileListComponent implements OnInit {
  public displayedColumns: string[];
  public dataSource: MediaModel[] = [];
  inputFieldText?: string;

  models: MediaModel[] = new Array<MediaModel>();
  filteredModels: MediaModel[] = new Array<MediaModel>();
  isAdmin: boolean;

  constructor(public dialog: MatDialog, private mediaService: MediaService, private storageService: StorageService, private snackBar: MatSnackBar,
              private router: Router) {

    this.isAdmin = storageService.getUser()?.role === UserRole.Admin;
    this.displayedColumns = this.isAdmin ? ['name', 'uploaderName', 'uploadDate', 'numberOfComments', 'modify', 'delete'] : ['name', 'uploaderName', 'uploadDate', 'numberOfComments'];
  }

  ngOnInit(): void {
    this.getMediaItems();
  }

  getMediaItems() {
    this.inputFieldText = "";
    this.mediaService.getFiles()
      .subscribe((items: MediaDTO[]) => {
        this.models = items.map((item: MediaDTO) => {
          return {
            id: item.fileId,
            fileName: item.fileName,
            uploaderName: item.uploader.fullName,
            uploadDate: item.uploadDate,
            numberOfComments: item.comments.length
          }
        });
        this.dataSource = this.models;
      })
  }

  onRowClicked(row: MediaModel) {
    this.router.navigate(['details', row.id], {state: row});
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim();
    if (!filterValue) {
      this.getMediaItems();
      return;
    }
    this.mediaService.searchFile(filterValue)
      .subscribe((items: MediaDTO[]) => {
        this.filteredModels = items.map((item: MediaDTO) => {
          return {
            id: item.fileId,
            fileName: item.fileName,
            uploaderName: item.uploader.fullName,
            uploadDate: item.uploadDate,
            numberOfComments: item.comments.length
          }
        });
        this.dataSource = this.filteredModels;
      });
  }

  clearSearch() {
    this.inputFieldText = "";
    this.getMediaItems();
  }

  onUploadClick() {
    const dialogRef = this.dialog.open(FileUploadComponent, {
      width: '310px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.event == DialogBoxAction.Cancel) {
        return;
      } else {
        this.getMediaItems();
      }
    });
  }

  onModifyClick(event: Event, media: MediaModel) {
    event.stopPropagation();
    this.openDialog(OpenDialogReason.Modify, media);
  }

  onDeleteClick(event: Event, media: MediaModel) {
    event.stopPropagation();
    this.openDialog(OpenDialogReason.Delete, media);
  }

  openDialog(reason: OpenDialogReason, media: MediaModel) {
    const dialogData: DialogBoxData = {
      id: media.id,
      dialogTitle: reason == OpenDialogReason.Modify ? "Edit name of the file" : "Are you sure to delete?",
      confirmButtonTitle: reason == OpenDialogReason.Modify ? "Confirm" : "Delete",
      inputFieldText: (reason == OpenDialogReason.Modify ? media.fileName : undefined)
    };
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '260px',
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.event == DialogBoxAction.Cancel) {
        return;
      }
      switch (reason) {
        case OpenDialogReason.Modify:
          this.updateRowData(result.data);
          break;
        case OpenDialogReason.Delete:
          this.deleteRowData(result.data.id);
          break;
        default:
          break;
      }
    });
  }

  addRowData(newMedia: MediaModel) {
    this.models.push(newMedia);
    this.dataSource = this.models
  }

  updateRowData(data: DialogBoxData) {
    if (!data.inputFieldText) {
      return;
    }
    const editDTO: EditFileDTO = {
      fileName: data.inputFieldText
    };
    this.mediaService.modifyFile(editDTO, data.id)
      .subscribe(() => {
        this.snackBar.open("Modified successfully!", undefined, {
          duration: 2000,
        });
        this.dataSource = this.dataSource.filter((value) => {
          if (value.id == data.id && data.inputFieldText) {
            value.fileName = data.inputFieldText;
          }
          return true;
        });
        this.models = this.dataSource;
      });
  }

  deleteRowData(id: number) {
    this.mediaService.deleteFile(id)
      .subscribe(() => {
        this.snackBar.open("Deleted successfully!", undefined, {
          duration: 2000,
        });
        this.dataSource = this.dataSource.filter((value) => {
          return value.id != id;
        });
        this.models = this.dataSource;
      });
  }
}
