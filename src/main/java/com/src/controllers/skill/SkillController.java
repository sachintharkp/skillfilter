package com.src.controllers.skill;

import com.src.controllers.user.UserController;
import com.src.models.skill.SkillRequest;
import com.src.models.skill.SkillResponse;
import com.src.service.skill.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    @PostMapping("/add")
    public ResponseEntity<SkillResponse> addSkill(@Valid @RequestBody SkillRequest skillRequest){
    LOGGER.info("Request received at user resource to add a skill");
        try {
            return ResponseEntity.ok().body( skillService.addSkill(skillRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
