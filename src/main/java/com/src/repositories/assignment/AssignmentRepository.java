package com.src.repositories.assignment;

import com.src.models.assignment.AssignmentEntity;
import com.src.models.assignment.UserAssignmentResultsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity,Long> {

    Optional<AssignmentEntity> findByAssignmentId(Long assignmentId);

    @Query(nativeQuery = true)
    List<UserAssignmentResultsDto> getUserAssignmentDetails(@Param("userId") Long userId);

    @Query(value = "Select * from assignment_tab where no_seats > 0",nativeQuery = true)
    List<AssignmentEntity> findAllAssignmentWithSeats();

}
