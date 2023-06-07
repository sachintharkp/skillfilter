package com.src.repositories.user;

import com.src.models.skill.SkillEntity;
import com.src.models.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(Long userId);

    List<SkillEntity> FindUserSkills(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM user_util u where u.role != \"manager\"",nativeQuery = true)
    List<UserEntity> findAllUsers();

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findOneByUsernameAndPassword(String username, String password);
}
