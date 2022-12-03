import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { StorageService } from 'src/app/services/authentication/storage.service';
import { MediaDTO, MediaService, UserRole } from 'src/app/services/networking';

export interface MediaModel { 
  id?: number;
  fileName?: string;
  uploaderName?: string;
  uploadDate?: Date;
  numberOfComments?: number;
}

const TEST_MEDIA_DATA: MediaModel[] = [
  {id: 0, fileName: 'Hydrogen', uploaderName: 'H', uploadDate: new Date(), numberOfComments: 2},
  {id: 1, fileName: 'Helium', uploaderName: 'A', uploadDate: new Date(), numberOfComments: 4},
  {id: 2, fileName: 'Nice', uploaderName: 'D', uploadDate: new Date(), numberOfComments: 12},
  {id: 3, fileName: 'WOW', uploaderName: 'R', uploadDate: new Date(), numberOfComments: 0},
  {id: 4, fileName: 'Funk', uploaderName: 'X', uploadDate: new Date(), numberOfComments: 0}
];

@Component({
  selector: 'ciff-clean-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss'],
  providers: [MediaService]
})

export class FileListComponent {
  public displayedColumns: string[];
  public dataSource = TEST_MEDIA_DATA;

  models: MediaModel[] = new Array<MediaModel>();
  filteredModels: MediaModel[] = new Array<MediaModel>();
  isAdmin: boolean;

  constructor(private mediaService: MediaService, private storageService: StorageService) {
    this.isAdmin = storageService.getUser()?.role === UserRole.Admin;
    this.isAdmin = true;
    this.displayedColumns = this.isAdmin ? ['name', 'uploaderName', 'uploadDate', 'numberOfComments', 'modify', 'delete'] : ['name', 'uploaderName', 'uploadDate', 'numberOfComments'];
  }

  ngOnInit(): void {
    // TODO: uncomment below upon backend is ready
    // this.getMediaItems();
  }

  getMediaItems() {
    this.mediaService.getFiles()
      .subscribe((items: MediaDTO[]) => {
        this.models = items.map((item: MediaDTO) => {
          return {
            id: item.fileId,
            fileName: item.fileName,
            uploaderName: item.uploader?.fullName,
            uploadDate: item.uploadDate,
            numberOfComments: item.comments?.length
          }
        });
        this.dataSource = this.models;
      })
  }

  onRowClicked(row: MediaModel) {
    console.log('Row clicked: ', row);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    console.log('Filtering for: ', filterValue);
  }

  onUploadClick() {
    console.log('Upload clicked');
  }

  onModifyClick(event: Event, id: number) {
    event.stopPropagation();
    console.log('Modify clicked ', id);
    

  }

  onDeleteClick(event: Event, id: number) {
    event.stopPropagation();
    console.log('Delete clicked ', id);
    
  }
}
