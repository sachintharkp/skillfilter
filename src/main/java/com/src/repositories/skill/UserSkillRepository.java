package com.src.repositories.skill;

import com.src.models.skill.SkillEntity;
import com.src.models.skill.UserSkillEntity;
import com.src.models.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserSkillRepository extends CrudRepository<UserSkillEntity,Long> {

    List<UserSkillEntity> findByUserAndSkill(UserEntity user, SkillEntity skill);

    @Query(value = "SELECT * FROM user_skill_tab users WHERE users.skill_id IN :skills" ,nativeQuery = true)
    List<UserSkillEntity> findUsersBySkillList(@Param("skills") Collection<Long> skills);

    @Query(value = "SELECT * FROM user_skill_tab users WHERE users.user_id =:userId" ,nativeQuery = true)
    List<UserSkillEntity> findUsersSkillsByUser(@Param("userId") Long userId);
}
