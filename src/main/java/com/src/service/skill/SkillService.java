package com.src.service.skill;

import com.src.exception.skill.SkillNotFoundException;
import com.src.models.assignment.UserAssignmentResultsDto;
import com.src.models.skill.*;
import com.src.models.user.SkilledUsersResponse;
import com.src.models.user.UserAssignmentDetailResponse;
import com.src.models.user.UserEntity;
import com.src.repositories.assignment.AssignmentRepository;
import com.src.repositories.skill.SkillRepository;
import com.src.repositories.skill.UserSkillRepository;
import com.src.repositories.user.UserRepository;
import com.src.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public SkillResponse addSkill(SkillRequest skillRequest) {

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

    public List<SkillResponse> getSkills(){
        List<SkillResponse> skillResponses = new ArrayList<>();
        List<SkillEntity> skillEntities  = skillRepository.findAll();
        skillEntities.forEach(skillEntity -> {
            SkillResponse skillEach = new SkillResponse();
            skillEach.setSkillId(skillEntity.getSkillId());
            skillEach.setSkillSdesc(skillEntity.getSkillSdesc());
            skillEach.setSkillLdesc(skillEntity.getSkillLdesc());
            skillResponses.add(skillEach);
        });
        return skillResponses;
    }


    public List<SkilledUsersResponse> getUsers(SkillSearchRequest skillSearchRequest) throws SkillNotFoundException {

        List<SkilledUsersResponse> userResponses = new ArrayList<>();
        List<Long> skilledUsersIds = new ArrayList<>();

        if (skillSearchRequest.getSkillId() != null) {
            List<Long> uniqueSkills = skillSearchRequest.getSkillId().stream().distinct().collect(Collectors.toList());
            List<SkillEntity> skillList = skillRepository.findBySkillIdIn(uniqueSkills);
            if (skillList.size() != uniqueSkills.size()) {
                throw new SkillNotFoundException("Skill/s you provided not Exist");
            }

            /*
            Selecting users who have any of the required skills.
             */
            List<UserSkillEntity> usersBySkillList = userSkillRepository.findUsersBySkillList(uniqueSkills);

            /*
            Create a list with all userIds have any of the required skills
             */
            if (!usersBySkillList.isEmpty()) {

            List<UserEntity> users = usersBySkillList.stream()
                        .map(UserSkillEntity::getUser)
                        .distinct()
                        .collect(Collectors.toList());

                users.forEach(user -> {

                    boolean isQualified = compareUserSkills(user.getUserId(), skillList);

                    boolean isActiveAssignment = userService.hasActiveAssignment(user.getUserId());

                    if (isQualified && !isActiveAssignment) {
                        SkilledUsersResponse skillsUser = new SkilledUsersResponse();
                        skillsUser.setUserid(user.getUserId());
                        skillsUser.setUsername(user.getUsername());
                        skillsUser.setFirstname(user.getFirstname());
                        skillsUser.setLastname(user.getLastname());
                        skillsUser.setYears(String.valueOf(user.getYears()));

                        List<SkillEntity> userSkills = userRepository.FindUserSkills(user.getUserId());
                        List<SkillResponse> skiillList = new ArrayList<>();

                        userSkills.forEach((skill) -> {
                            SkillResponse skillEach = new SkillResponse();
                            skillEach.setSkillId(skill.getSkillId());
                            skillEach.setSkillSdesc(skill.getSkillSdesc());
                            skillEach.setSkillLdesc(skill.getSkillLdesc());
                            skiillList.add(skillEach);
                        });
                        skillsUser.setSkillList(skiillList);

                        List<UserAssignmentResultsDto> usersAllAssignments = assignmentRepository.getUserAssignmentDetails(user.getUserId());
                        List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
                        usersAllAssignments.forEach(assignments -> {
                            UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
                            detailResponse.setActiveAssignmentId(assignments.getAssignmentId());
                            detailResponse.setCompanyName(assignments.getCompanyName());
                            detailResponse.setPosition(assignments.getPosition());
                            detailResponse.setStatus(assignments.getIsActive());
                            assignmentDetailResponse.add(detailResponse);
                        });

                        skillsUser.setActiveAssignment(assignmentDetailResponse);

                        userResponses.add(skillsUser);
                    }

                });
            }

        }

        return userResponses;
    }

    /*
    This  function check whether a given user has the required skill set.
     */
    public boolean compareUserSkills(Long userId, List<SkillEntity> requiredSkills) {

        /*
        List of skills have for a given user.
         */
        List<UserSkillEntity> usersBySkillList = userSkillRepository.findUsersSkillsByUser(userId);

        /*
        Checking whether required skill set is a subset of users skills.
         */

        List<UserSkillEntity> UsersSkills = usersBySkillList.stream()
                .filter(usersBySkill -> requiredSkills.stream()
                        .anyMatch(skills ->
                                skills.getSkillId() == usersBySkill.getSkill().getSkillId())).collect(Collectors.toList());
        ;

        /*
        when users skills are less than required skills return false for qualified user boolean.
         */
        if (UsersSkills.size() < requiredSkills.size()) {
            return false;
        } else {
            return true;
        }
    }
}

