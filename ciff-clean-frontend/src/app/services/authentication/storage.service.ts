import { Injectable } from '@angular/core';
import { UserDTO } from '../networking';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

export interface Token {
  sub: string,
  exp: number,
  userId: number
}

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public isTokenInvalidOrExpired(): boolean {
    let token = this.getToken();
    if(token == null) return true;
    const jwtData = token.split('.')[1];
    const decodedJwtJsonData = atob(jwtData);
    const decodedJwtData: Token = JSON.parse(decodedJwtJsonData);

    let now = new Date();
    return decodedJwtData.exp < Math.ceil(now.getTime() / 1000);
  }

  public saveUser(user: UserDTO): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): UserDTO | null {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }
}
