package com.src.controllers.skill;

import com.src.controllers.user.UserController;
import com.src.exception.skill.SkillNotFoundException;
import com.src.models.skill.SkillRequest;
import com.src.models.skill.SkillResponse;
import com.src.models.skill.SkillSearchRequest;
import com.src.models.user.SkilledUsersResponse;
import com.src.service.skill.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/skill")
@CrossOrigin
public class SkillController {

    @Autowired
    private SkillService skillService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

   @GetMapping("/getSkills")
    public ResponseEntity<List<SkillResponse>> getAllSkills(){
        LOGGER.info("Request received to get all skills");
        try{
            return ResponseEntity.ok().body(skillService.getSkills());
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/add")
    public ResponseEntity<SkillResponse> addSkill(@Valid @RequestBody SkillRequest skillRequest){
    LOGGER.info("Request received to add a skill");
        try {
            return ResponseEntity.ok().body( skillService.addSkill(skillRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/users")
    public ResponseEntity<List<SkilledUsersResponse>> getAllUsersBySkillId(@Valid @RequestBody SkillSearchRequest skillSearchRequest){
        try {
            return ResponseEntity.ok().body( skillService.getUsers(skillSearchRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SkillNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
