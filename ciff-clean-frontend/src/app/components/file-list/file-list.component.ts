import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

export interface MediaModel { 
  id: number;
  fileName: string;
  dateOfUpload: Date;
  uploaderName: string;
}

const TEST_MEDIA_DATA: MediaModel[] = [
  {id: 0, fileName: 'Hydrogen', dateOfUpload: new Date(), uploaderName: 'H'},
  {id: 1, fileName: 'Helium', dateOfUpload: new Date(), uploaderName: 'A'},
  {id: 2, fileName: 'Nice', dateOfUpload: new Date(), uploaderName: 'D'},
  {id: 3, fileName: 'WOW', dateOfUpload: new Date(), uploaderName: 'R'},
  {id: 4, fileName: 'Funk', dateOfUpload: new Date(), uploaderName: 'X'}
];

@Component({
  selector: 'ciff-clean-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss']
})

export class FileListComponent {
  public displayedColumns = ['name', 'dateOfUpload', 'uploaderName'];
  public dataSource = TEST_MEDIA_DATA;

  constructor() {
    // TODO: backend call
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
}
