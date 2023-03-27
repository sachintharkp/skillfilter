package com.src.service.user;

import com.src.exception.assignment.AssignmentNotFoundException;
import com.src.exception.skill.SkillNotFoundException;
import com.src.exception.user.UserNotFoundException;
import com.src.models.assignment.AssignmentEntity;
import com.src.models.assignment.UserAssignmentEntity;
import com.src.models.skill.SkillEntity;
import com.src.models.skill.SkillResponse;
import com.src.models.skill.UserSkillEntity;
import com.src.models.user.*;
import com.src.repositories.assignment.AssignmentRepository;
import com.src.repositories.assignment.UserAssignmentRepository;
import com.src.repositories.skill.SkillRepository;
import com.src.repositories.skill.UserSkillRepository;
import com.src.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserAssignmentRepository userAssignmentRepository;


    public UserResponse createUser(UserRequest user) throws SkillNotFoundException, AssignmentNotFoundException {


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstname(user.getFirstName());
        userEntity.setLastname(user.getLastName());
        userEntity.setYears(user.getYears());
        UserEntity createdUser = userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();

        Long activeAssignmentId = user.getActiveAssignmentId();

        if (activeAssignmentId != null) {
            AssignmentEntity userAssignment = addActiveAssignment(createdUser, activeAssignmentId);
            UserAssignmentDetailResponse assignmentDetailResponse = new UserAssignmentDetailResponse();
            assignmentDetailResponse.setActiveAssignmentId(userAssignment.getAssignmentId());
            assignmentDetailResponse.setCompanyName(userAssignment.getCompanyName());
            assignmentDetailResponse.setActive(true);
            userResponse.setActiveAssignment(assignmentDetailResponse);
        }

        if (user.getSkillList() != null) {

            List<SkillEntity> userSkills = addSkillsToUser(createdUser, user.getSkillList());
            List<SkillResponse> skiillList = new ArrayList<>();

            userSkills.forEach((skill) -> {
                SkillResponse skillEach = new SkillResponse();
                skillEach.setSkillId(skill.getSkillId());
                skillEach.setSkillSdesc(skill.getSkillSdesc());
                skillEach.setSkillLdesc(skill.getSkillLdesc());
                skiillList.add(skillEach);
            });
            userResponse.setSkillList(skiillList);
        }


        userResponse.setUserid(userEntity.getUserId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setPassword(userEntity.getPassword());
        userResponse.setFirstname(userEntity.getFirstname());
        userResponse.setLastname(userEntity.getLastname());
        userResponse.setYears(String.valueOf(userEntity.getYears()));


        return userResponse;

    }

    public List<SkillEntity> addSkillsToUser(UserEntity user, List<Long> skills) throws SkillNotFoundException {

        List<Long> uniqueSkills = skills.stream().distinct().collect(Collectors.toList());
        List<SkillEntity> skillList = skillRepository.findBySkillIdIn(uniqueSkills);
        if (skillList.size() != uniqueSkills.size()) {
            throw new SkillNotFoundException("Skill/s you provided not Exist");
        }

        skillList.forEach((skill) -> {
            List<UserSkillEntity> skillEntityList = userSkillRepository.findByUserAndSkill(user, skill);
            if (skillEntityList.isEmpty()) {
                UserSkillEntity userSkillEntity = new UserSkillEntity();
                userSkillEntity.setUser(user);
                userSkillEntity.setSkill(skill);
                userSkillRepository.save(userSkillEntity);
            }
        });
        return skillList;
    }

    public UserResponse addSkillsToExitingUser(UpdateUserSkillRequest userSkillRequest) throws SkillNotFoundException, UserNotFoundException {

        UserEntity userEntity = userRepository.findByUserId(userSkillRequest.getUserid());

        if (userEntity == null) {
            throw new UserNotFoundException("Invalid User");
        } else {
            List<SkillEntity> userSkills = addSkillsToUser(userEntity, userSkillRequest.getSkillId());
            UserResponse userResponse = new UserResponse();
            userResponse.setUserid(userEntity.getUserId());
            userResponse.setUsername(userEntity.getUsername());
            userResponse.setPassword(userEntity.getPassword());
            userResponse.setFirstname(userEntity.getFirstname());
            userResponse.setLastname(userEntity.getLastname());
            userResponse.setYears(String.valueOf(userEntity.getYears()));
            if (!userSkills.isEmpty()) {
                List<SkillResponse> skiillList = new ArrayList<>();
                userSkills.forEach((skill) -> {
                    SkillResponse skillEach = new SkillResponse();
                    skillEach.setSkillId(skill.getSkillId());
                    skillEach.setSkillSdesc(skill.getSkillSdesc());
                    skillEach.setSkillLdesc(skill.getSkillLdesc());
                    skiillList.add(skillEach);
                });
                userResponse.setSkillList(skiillList);
            }

            return userResponse;

        }
    }

    public AssignmentEntity addActiveAssignment(UserEntity user, Long activeAssignmentId) throws AssignmentNotFoundException {

        AssignmentEntity assignment = assignmentRepository.findByAssignmentId(activeAssignmentId);

        if (assignment == null) {
            throw new AssignmentNotFoundException("Assignment is not found");
        } else {
            UserAssignmentEntity assignmentEntity = new UserAssignmentEntity();
            assignmentEntity.setUser(user);
            assignmentEntity.setAssignment(assignment);
            assignmentEntity.setActive(true);
            userAssignmentRepository.save(assignmentEntity);
            return assignment;
        }

    }


}
