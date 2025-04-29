import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloak: Keycloak;

  // ✅ Define a private subject and a public observable
  private authenticatedSubject = new BehaviorSubject<boolean>(false);
  public authenticated$ = this.authenticatedSubject.asObservable();

  constructor() {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8181', // ✅ Correct URL
      realm: 'notification_system',
      clientId: 'notification_system_users'
    });
  }

  init(): Promise<boolean> {
    return this.keycloak.init({
      onLoad: 'login-required',
      pkceMethod: 'S256',
      checkLoginIframe: false }).then(authenticated => {
      this.authenticatedSubject.next(authenticated);
      if (authenticated) {
        localStorage.setItem('token', this.keycloak.token!);
      }
      return authenticated;
    });
  }

  login() {
    return this.keycloak.login();
  }

  logout() {
    this.keycloak.logout().then(() => {
      localStorage.removeItem('token');
    });
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  getEmail(): string | undefined {
    return 'this.keycloak.tokenParsed?';
  }
}
