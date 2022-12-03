import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MediaModel} from "../file-list/file-list.component";
import {MediaService} from "../../services/networking";

@Component({
  selector: 'ciff-clean-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
  providers: [MediaService]
})
export class DetailsComponent implements OnInit {
  file: MediaModel | undefined = undefined;
  constructor(private route: ActivatedRoute, private mediaService: MediaService) {
  }

  ngOnInit(): void {
    const fileId = +(this.route.snapshot.paramMap.get('id') ?? -1);
    this.file = history.state;
    console.log(this.file);
    this.initFile(fileId ?? "");
  }

  initFile(id: number): void {
    this.mediaService.downloadFile(id).subscribe()
  }
}
