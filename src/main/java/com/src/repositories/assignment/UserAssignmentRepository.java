package com.src.repositories.assignment;

import com.src.models.assignment.UserAssignmentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAssignmentRepository extends CrudRepository<UserAssignmentEntity,Long> {


    @Modifying
    @Query(value = "update user_assignment_tab u set u.is_active = false where user_id=:userId",nativeQuery = true)
    @Transactional
    void deactivateOtherAssignmentOfUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_assignment_tab WHERE user_id =:userId  && is_active = 1" ,nativeQuery = true)
    Optional<List<UserAssignmentEntity>> findUsersHasActiveAssignment(@Param("userId") Long userId);

}
