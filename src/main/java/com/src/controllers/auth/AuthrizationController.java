package com.src.controllers.auth;

import com.src.exception.user.InvalidPasswordException;
import com.src.exception.user.InvalidUsernameException;
import com.src.models.auth.LoginRequest;
import com.src.models.auth.LoginResponse;
import com.src.models.auth.LogoutRequest;
import com.src.models.auth.LogoutResponse;
import com.src.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@Validated
@RestController
@CrossOrigin
public class AuthrizationController {
    private static final Logger LOGGER = Logger.getLogger(AuthrizationController.class.getSimpleName());


    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        LOGGER.info("Request received at user login");
        try {
            return ResponseEntity.ok().body( authService.login(loginRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidPasswordException e) {
            throw new RuntimeException(e);
        } catch (InvalidUsernameException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> loginUser(@Valid @RequestBody LogoutRequest logoutRequest){
        LOGGER.info("Request received at user logout");
        try {
            return ResponseEntity.ok().body( authService.logout(logoutRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
