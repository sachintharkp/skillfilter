package com.src.repositories.assignment;

import com.src.models.assignment.UserAssignmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAssignmentRepository extends CrudRepository<UserAssignmentEntity,Long> {
}
