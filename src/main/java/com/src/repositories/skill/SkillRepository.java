package com.src.repositories.skill;

import com.src.models.skill.SkillEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends CrudRepository<SkillEntity,Long> {

   List<SkillEntity> findBySkillIdIn(List<Long> skillId);

   List<SkillEntity> findAll();

}
