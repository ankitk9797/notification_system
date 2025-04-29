import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8181',       // ✅ correct URL
  realm: 'notification_system',       // ✅ your realm
  clientId: 'notification_system_users' // ✅ your client ID
});

keycloak.init({ onLoad: 'login-required' })
  .then(authenticated => {
    console.log('Authenticated:', authenticated);
    localStorage.setItem('token', keycloak.token || '');
    localStorage.setItem('email', keycloak.tokenParsed?.['email'] || '');

    if (authenticated) {
      bootstrapApplication(AppComponent, appConfig)
        .catch(err => console.error(err));
    }
  })
  .catch(err => {
    console.log("error");
    console.error('Error initializing Keycloak:', err);
  });
