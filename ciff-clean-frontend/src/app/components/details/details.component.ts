import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CreateCommentDTO, MediaDTO, MediaService, UserRole} from "../../services/networking";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BehaviorSubject} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'ciff-clean-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
  providers: [MediaService]
})
export class DetailsComponent implements OnInit {
  file: MediaDTO | undefined = undefined;
  media: Blob | undefined | any = undefined;
  showCommentArea = false;
  commentText = "";

  constructor(private route: ActivatedRoute, private mediaService: MediaService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    const fileId = +(this.route.snapshot.paramMap.get('id') ?? -1);
    this.initFile(fileId ?? "");
    this.initDetails(fileId);
  }

  initFile(id: number): void {
    this.mediaService.downloadFile(id).subscribe(data => {
      let file = new Blob([data], {type: 'image/gif'});
      let reader = new FileReader();
      reader.readAsDataURL(file);
      let result = new BehaviorSubject<string | ArrayBuffer | null>('');
      result.subscribe(src => this.media = src);
      reader.onloadend = function () {
        result.next(reader.result);
      }
    });
  }

  initDetails(id: number): void {
    this.mediaService.getMediaById(id).subscribe(data => {
      this.file = data;
    });
  }

  comment() {
    const createComment: CreateCommentDTO = {
      text: this.commentText,
      fileId: this.file?.fileId
    };
    this.mediaService.commentFile(createComment).subscribe(() => {
      this.commentText = "";
      this.initDetails(this.file!.fileId);
    }, (error) => {
      if (error instanceof HttpErrorResponse) {
        this.snackBar.open('Comment was unsuccessful', undefined, {
          duration: 2000,
        });
      }
    })
  }
}
