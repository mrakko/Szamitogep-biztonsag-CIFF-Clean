import {Component} from '@angular/core';
import {StorageService} from "../../services/authentication/storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'ciff-clean-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [StorageService]
})
export class HeaderComponent {
  constructor(private storageService: StorageService, private router: Router) {
  }

  logOut(): void {
    this.storageService.signOut();
    this.router.navigate(['/login']);
  }
}
