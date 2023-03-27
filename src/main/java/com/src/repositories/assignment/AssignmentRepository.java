package com.src.repositories.assignment;

import com.src.models.assignment.AssignmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity,Long> {

    AssignmentEntity findByAssignmentId(Long assignmentId);

}
