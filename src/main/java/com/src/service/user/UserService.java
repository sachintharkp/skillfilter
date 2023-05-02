package com.src.service.user;

import com.src.exception.assignment.AssignmentNotFoundException;
import com.src.exception.assignment.NoSeatsAvailableException;
import com.src.exception.skill.SkillNotFoundException;
import com.src.exception.user.UserNotFoundException;
import com.src.models.assignment.AssignmentEntity;
import com.src.models.assignment.UserAssignmentEntity;
import com.src.models.assignment.UserAssignmentResultsDto;
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


    public UserResponse createUser(UserRequest user) throws SkillNotFoundException, AssignmentNotFoundException, NoSeatsAvailableException {


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstname(user.getFirstName());
        userEntity.setLastname(user.getLastName());
        userEntity.setYears(user.getYears());
        UserEntity createdUser = userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();

        Long activeAssignmentId = user.getActiveAssignmentId();

        if (activeAssignmentId != 0) {
            List<UserAssignmentResultsDto> userAllAssignments = addActiveAssignment(createdUser, activeAssignmentId);
            List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();

            userAllAssignments.forEach(assignments -> {
                UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
                detailResponse.setActiveAssignmentId(assignments.getAssignmentId());
                detailResponse.setCompanyName(assignments.getCompanyName());
                detailResponse.setPosition(assignments.getPosition());
                detailResponse.setStatus(assignments.getIsActive());
                assignmentDetailResponse.add(detailResponse);
            });

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

    public UserResponse updateUser(UpdateUserProfileRequest userProfileRequest) throws UserNotFoundException, SkillNotFoundException, AssignmentNotFoundException, NoSeatsAvailableException {

        List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
        List<SkillResponse> skiillList = new ArrayList<>();

        UserResponse userResponse = new UserResponse();

        UserEntity userEntity = userRepository.findByUserId(userProfileRequest.getUserid());

        if (userEntity == null) {
            throw new UserNotFoundException("Invalid User");
        } else {

            userEntity.setUsername(userProfileRequest.getUsername());
            userEntity.setFirstname(userProfileRequest.getFirstName());
            userEntity.setLastname(userProfileRequest.getLastName());
            userEntity.setYears(userProfileRequest.getYears());
            userRepository.save(userEntity);
            skiillList =addSkillsToExitingUser(userProfileRequest);
            assignmentDetailResponse = updateUsersAssignment(userProfileRequest);

            userResponse.setUserid(userEntity.getUserId());
            userResponse.setUsername(userEntity.getUsername());
            userResponse.setPassword(userEntity.getPassword());
            userResponse.setFirstname(userEntity.getFirstname());
            userResponse.setLastname(userEntity.getLastname());
            userResponse.setYears(String.valueOf(userEntity.getYears()));
            userResponse.setActiveAssignment(assignmentDetailResponse);
            userResponse.setSkillList(skiillList);
        }
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

    public  List<SkillResponse> addSkillsToExitingUser(UpdateUserProfileRequest userSkillRequest) throws SkillNotFoundException, UserNotFoundException {

        UserEntity userEntity = userRepository.findByUserId(userSkillRequest.getUserid());
        List<SkillResponse> skiillList = new ArrayList<>();
        if (userEntity == null) {
            throw new UserNotFoundException("Invalid User");
        } else {
            List<SkillEntity> userSkills = addSkillsToUser(userEntity, userSkillRequest.getSkillId());
                if (!userSkills.isEmpty()) {
                    userSkills.forEach((skill) -> {
                    SkillResponse skillEach = new SkillResponse();
                    skillEach.setSkillId(skill.getSkillId());
                    skillEach.setSkillSdesc(skill.getSkillSdesc());
                    skillEach.setSkillLdesc(skill.getSkillLdesc());
                    skiillList.add(skillEach);
                });
            }
            return skiillList;

        }
    }

    public List<UserAssignmentResultsDto> addActiveAssignment(UserEntity user, Long activeAssignmentId) throws AssignmentNotFoundException, NoSeatsAvailableException {

        AssignmentEntity assignment = assignmentRepository.findByAssignmentId(activeAssignmentId);

        if (assignment == null) {
            throw new AssignmentNotFoundException("Assignment is not found");
        } else {
            int availableSeats = assignment.getSeats();
            if(availableSeats > 0 ){
                userAssignmentRepository.deactivateOtherAssignmentOfUser(user.getUserId());
                UserAssignmentEntity assignmentEntity = new UserAssignmentEntity();
                assignmentEntity.setUser(user);
                assignmentEntity.setAssignment(assignment);
                assignmentEntity.setActive(true);
                userAssignmentRepository.save(assignmentEntity);
                assignment.setSeats(availableSeats-1);
                assignmentRepository.save(assignment);
                return assignmentRepository.getUserAssignmentDetails(user.getUserId());
            }
            else{
                throw new NoSeatsAvailableException ("There are no seats available in this assignment for this user.");
            }
        }
    }

    public  List<UserAssignmentDetailResponse> updateUsersAssignment(UpdateUserProfileRequest updateUserAssignmentRequest) throws UserNotFoundException, AssignmentNotFoundException, NoSeatsAvailableException {

        UserEntity user = userRepository.findByUserId(updateUserAssignmentRequest.getUserid());
        if(user == null){
            throw new UserNotFoundException("Invalid User");
        }
        else{
            List<UserAssignmentResultsDto> usersAllAssignments = addActiveAssignment(user,updateUserAssignmentRequest.getActiveAssignmentId());
            List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
            usersAllAssignments.forEach(assignments -> {
                UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
                detailResponse.setActiveAssignmentId(assignments.getAssignmentId());
                detailResponse.setCompanyName(assignments.getCompanyName());
                detailResponse.setPosition(assignments.getPosition());
                detailResponse.setStatus(assignments.getIsActive());
                assignmentDetailResponse.add(detailResponse);
            });
           return assignmentDetailResponse;
        }


    }

    /*
    * This service only provide users initial details except skills and Assignments for the search user.
    * */
    public List<UserResponse> getAllUsers(){

        List<UserResponse> allUsers = new ArrayList<>();

        List<UserEntity> users = userRepository.findAll();

        users.forEach(user -> {
           UserResponse userResponse = new UserResponse();
           userResponse.setUserid(user.getUserId());
           userResponse.setFirstname(user.getFirstname());
           userResponse.setLastname(user.getLastname());
           userResponse.setUsername(user.getUsername());
           userResponse.setYears(String.valueOf(user.getYears()));
           allUsers.add(userResponse);
        });
        return  allUsers;
    }

    public UserResponse getUser(Long userId){

        UserEntity user = userRepository.findByUserId(userId);

        List<SkillEntity> userSkills = userRepository.FindUserSkills(user.getUserId());
        List<SkillResponse> skiillList = new ArrayList<>();
        if (!userSkills.isEmpty())
        userSkills.forEach((skill) -> {
            SkillResponse skillEach = new SkillResponse();
            skillEach.setSkillId(skill.getSkillId());
            skillEach.setSkillSdesc(skill.getSkillSdesc());
            skillEach.setSkillLdesc(skill.getSkillLdesc());
            skiillList.add(skillEach);
        });

        List<UserAssignmentResultsDto> usersAllAssignments = assignmentRepository.getUserAssignmentDetails(user.getUserId());
        List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
        if(!usersAllAssignments.isEmpty())
        usersAllAssignments.forEach(assignments -> {
            UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
            detailResponse.setActiveAssignmentId(assignments.getAssignmentId());
            detailResponse.setCompanyName(assignments.getCompanyName());
            detailResponse.setPosition(assignments.getPosition());
            detailResponse.setStatus(assignments.getIsActive());
            assignmentDetailResponse.add(detailResponse);
        });

        UserResponse response = new UserResponse();
        response.setUserid(user.getUserId());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setUsername(user.getUsername());
        response.setYears(String.valueOf(user.getYears()));
        response.setActiveAssignment(assignmentDetailResponse);
        response.setSkillList(skiillList);


        return response;
    }

}
