package com.notification_system.user_management.controller;

import com.notification_system.user_management.client.StockClient;
import com.notification_system.user_management.dto.RequestUserDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private StockClient stockClient;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RequestUserDto dto){
        List<UserRepresentation> list = keycloak.realm("notification_system")
                .users()
                .list();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(dto.getEmail());
        userRepresentation.setUsername(dto.getEmail());
        userRepresentation.setFirstName(dto.getName());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(dto.getPassword());
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        Response response = keycloak.realm("notification_system").users().create(userRepresentation);

        if (response.getStatus() == 201) {
            stockClient.createUser(dto.getEmail());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User created successfully");
        } else {
            return ResponseEntity
                    .status(response.getStatus())
                    .body("Failed to create user: " + response.getStatusInfo().getReasonPhrase());
        }
    }
}
