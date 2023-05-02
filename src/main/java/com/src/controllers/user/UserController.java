package com.src.controllers.user;

import com.src.exception.assignment.AssignmentNotFoundException;
import com.src.exception.assignment.NoSeatsAvailableException;
import com.src.exception.skill.SkillNotFoundException;
import com.src.exception.user.UserNotFoundException;
import com.src.models.user.*;
import com.src.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        LOGGER.info("Request received at user resource to create a user");
        try {
            return ResponseEntity.ok().body( userService.createUser(userRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SkillNotFoundException e) {
            throw new RuntimeException(e);
        } catch (AssignmentNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSeatsAvailableException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public  ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserProfileRequest userProfileRequest){
        LOGGER.info("Request received at user resource to update a user");
        try {
            return ResponseEntity.ok().body( userService.updateUser(userProfileRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundException | SkillNotFoundException | AssignmentNotFoundException |
                 NoSeatsAvailableException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getUsers")
    public  ResponseEntity<List<UserResponse>> getAllUsers(){
        LOGGER.info("Request received at user resource to update a user");
        try {
            return ResponseEntity.ok().body( userService.getAllUsers());
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserResponse> getUser(@Valid @RequestParam("userid")  Long userId){
        LOGGER.info("Request received at user resource to get a user ");
        try {
            return ResponseEntity.ok().body( userService.getUser(userId));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
