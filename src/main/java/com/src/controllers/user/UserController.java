package com.src.controllers.user;

import com.src.exception.skill.SkillNotFoundException;
import com.src.exception.user.UserNotFoundException;
import com.src.models.user.UpdateUserSkillRequest;
import com.src.models.user.UserRequest;
import com.src.models.user.UserResponse;
import com.src.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        }
    }

    @PostMapping("/addSkill")
    public  ResponseEntity<UserResponse> addSkillToUser(@Valid @RequestBody UpdateUserSkillRequest userSkillRequest){
        LOGGER.info("Request received at user resource to create a user");
        try {
            return ResponseEntity.ok().body( userService.addSkillsToExitingUser(userSkillRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SkillNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
