package com.src.service.user;

import com.src.exception.skill.SkillNotFoundException;
import com.src.models.skill.SkillEntity;
import com.src.models.user.UserEntity;
import com.src.models.user.UserRequest;
import com.src.models.user.UserResponse;
import com.src.repositories.skill.SkillRepository;
import com.src.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;


    public UserResponse createUser(UserRequest user) throws SkillNotFoundException {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstname(user.getFirstName());
        userEntity.setLastname(user.getLastName());
        userEntity.setYears(user.getYears());
        if(user.getSkillList()!=null) {
            List<SkillEntity> skillList = skillRepository.findBySkillIdIn(user.getSkillList());
            if(skillList.isEmpty()){
               throw new SkillNotFoundException("Skill/s not Exist");
            }
            userEntity.setSkills(skillList);
        }
        userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserid(userEntity.getUserId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setPassword(userEntity.getPassword());
        userResponse.setFirstname(userEntity.getFirstname());
        userResponse.setLastname(userEntity.getLastname());
        userResponse.setYears(String.valueOf(userEntity.getYears()));

        return userResponse;

    }

}
