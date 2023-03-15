package com.src.repositories.user;

import com.src.models.skill.SkillEntity;
import com.src.models.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(Long userId);

    List<SkillEntity> FindUserSkills(@Param("userId") Long userId);




}
