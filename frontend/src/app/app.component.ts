import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { KeycloakService } from './auth/keycloak.service';
import {RouterOutlet} from '@angular/router';
import {StocksDashboardComponent} from './stocks-dashboard/stocks-dashboard.component';
import {AuthPopupComponent} from './auth-popup/auth-popup.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, StocksDashboardComponent, AuthPopupComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  private keycloakService = inject(KeycloakService);
  isAuthenticated = true;
  email = '';

  login() {
    this.keycloakService.login();
  }

  logout() {
    this.keycloakService.logout();
  }

  ngOnInit(): void {
    console.log(localStorage.getItem('email'));
  }
}
