package com.src.service.assignment;

import com.src.models.assignment.AddAssignmentRequest;
import com.src.models.assignment.AssignmentEntity;
import com.src.models.user.UserAssignmentDetailResponse;
import com.src.repositories.assignment.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public UserAssignmentDetailResponse addNewAssignment(AddAssignmentRequest assignmentRequest){

        AssignmentEntity assignmentEntity = new AssignmentEntity();
        assignmentEntity.setCompanyName(assignmentRequest.getCompanyName());
        assignmentEntity.setPosition(assignmentRequest.getPosition());
        assignmentEntity.setSeats(assignmentRequest.getSeats());
        AssignmentEntity newAssignment = assignmentRepository.save(assignmentEntity);

        UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
        detailResponse.setActiveAssignmentId(newAssignment.getAssignmentId());
        detailResponse.setCompanyName(newAssignment.getCompanyName());
        detailResponse.setPosition(newAssignment.getPosition());
        detailResponse.setSeats(newAssignment.getSeats());
        return  detailResponse;
    }

    public List<UserAssignmentDetailResponse> getAllAssignments(){
      List<AssignmentEntity> assignments = assignmentRepository.findAllAssignmentWithSeats();
      List<UserAssignmentDetailResponse> assignmentListResponse = new ArrayList<>();

      assignments.forEach( assignment -> {
          UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
          detailResponse.setActiveAssignmentId(assignment.getAssignmentId());
          detailResponse.setCompanyName(assignment.getCompanyName());
          detailResponse.setPosition(assignment.getPosition());
          detailResponse.setSeats(assignment.getSeats());
          assignmentListResponse.add(detailResponse);
      }
      );
      return assignmentListResponse;
    }
}
