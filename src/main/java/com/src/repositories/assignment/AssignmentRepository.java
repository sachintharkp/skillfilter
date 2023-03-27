package com.src.repositories.assignment;

import com.src.models.assignment.AssignmentEntity;
import com.src.models.assignment.UserAssignmentResultsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity,Long> {

    AssignmentEntity findByAssignmentId(Long assignmentId);

    @Query(nativeQuery = true)
    List<UserAssignmentResultsDto> getUserAssignmentDetails(@Param("userId") Long userId);

}