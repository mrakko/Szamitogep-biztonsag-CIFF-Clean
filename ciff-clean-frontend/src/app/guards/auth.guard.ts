import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import { TokenStorageService } from '../services/authentication/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private tokenService: TokenStorageService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // TODO: Uncomment below when token acquisition and authentication are working
    
    // if (this.tokenService.getToken() == null || this.tokenService.getUser() == null) {
    //   this.router.navigate(['login']);
    //   return false;
    // }
    return true;
  }

}
