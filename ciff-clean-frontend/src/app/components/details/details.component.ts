import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CreateCommentDTO, MediaDTO, MediaService, UserRole} from "../../services/networking";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'ciff-clean-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
  providers: [MediaService]
})
export class DetailsComponent implements OnInit {
  file: MediaDTO | undefined = {
    comments: [{
      id: 1,
      text: 'valami kis komment',
      commenter: {
        id: 2,
        role: UserRole.Regular,
        fullName: "commenter",
        profileImage: undefined,
      }
    }, {
      id: 3,
      text: 'valami kicsit hosszabb komment is legy sdf sf se fs f greklgr stgiesofgesaf resgorehf egreiogweaőf greipgoaőf regprfen pls',
      commenter: {
        id: 3,
        role: UserRole.Regular,
        fullName: "valaki teszt",
        profileImage: undefined,
      }
    }],
    fileId: 0,
    fileName: "file.gif",
    uploadDate: new Date(),
    uploader: {
      id: 2,
      role: UserRole.Regular,
      fullName: "commenter",
      profileImage: undefined,
    }
  }
  media: Blob | undefined = undefined;
  showCommentArea = false;
  commentText = "";

  constructor(private route: ActivatedRoute, private mediaService: MediaService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    const fileId = +(this.route.snapshot.paramMap.get('id') ?? -1);

    console.log(this.file);
    this.initFile(fileId ?? "");
    this.initDetails(fileId);
  }

  initFile(id: number): void {
    //TODO uncomment
    /*this.mediaService.downloadFile(id).subscribe(data => {
          this.media = data;
        });*/
  }

  initDetails(id: number): void {
    //TODO uncomment
    /*this.mediaService.getFiles(id).subscribe(data => {
        this.file = data;
      });*/
  }

  comment() {
    console.log('commented', this.commentText);
    const createComment: CreateCommentDTO = {
      text: this.commentText,
      fileId: this.file?.fileId
    };
    this.mediaService.commentFile(createComment).subscribe(() => {
      this.commentText = "";
      this.initDetails(this.file!.fileId);
    })
  }
}
