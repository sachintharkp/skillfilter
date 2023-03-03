package com.src.service.skill;

import com.src.models.skill.SkillEntity;
import com.src.models.skill.SkillRequest;
import com.src.models.skill.SkillResponse;
import com.src.repositories.skill.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public SkillResponse addSkill(SkillRequest skillRequest){

        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setSkillSdesc(skillRequest.getSkillSdesc());
        skillEntity.setSkillLdesc(skillRequest.getSkillLdesc());

        skillRepository.save(skillEntity);

        SkillResponse skillResponse = new SkillResponse();
        skillResponse.setSkillId(skillEntity.getSkillId());
        skillResponse.setSkillSdesc(skillEntity.getSkillSdesc());
        skillResponse.setSkillLdesc(skillEntity.getSkillLdesc());

        return skillResponse;
    }
}
