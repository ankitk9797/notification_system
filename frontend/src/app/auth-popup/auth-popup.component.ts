import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClient, HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-auth-popup',
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './auth-popup.component.html',
  styleUrl: './auth-popup.component.css'
})
export class AuthPopupComponent {
  showModal = false;
  mode: 'login' | 'register' = 'login';

  constructor(private http: HttpClient) {}

  formData = {
    name: '',
    email: '',
    password: ''
  };

  openModal(mode: 'login' | 'register') {
    this.mode = mode;
    this.formData = { name: '', email: '', password: '' };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  submit() {
    if (this.mode === 'login') {
      console.log('Login with', this.formData.email, this.formData.password);
      alert(`Logging in as ${this.formData.email}`);
    } else {
      console.log('Register with', this.formData);
      alert(`Registering ${this.formData.name}`);
    }
    this.closeModal();
  }
}
