package com.src.controllers.assignment;

import com.src.controllers.user.UserController;
import com.src.models.assignment.AddAssignmentRequest;
import com.src.models.user.UserAssignmentDetailResponse;
import com.src.service.assignment.AssignmentService;
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
@RequestMapping("/assignment")
@CrossOrigin
public class AssignmentController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("/add")
    public ResponseEntity<UserAssignmentDetailResponse> addAssignment(@Valid @RequestBody AddAssignmentRequest addAssignmentRequest){
        LOGGER.info("Request received to add a new assignment organization");
        try {
            return ResponseEntity.ok().body( assignmentService.addNewAssignment(addAssignmentRequest));
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAssignments")
    public ResponseEntity<List<UserAssignmentDetailResponse>> getAssignments(){
        LOGGER.info("Request received to get all Assignments");
        try{
            return ResponseEntity.ok().body(assignmentService.getAllAssignmentsWithSeats());
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
