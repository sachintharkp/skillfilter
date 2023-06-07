package com.src.service.user;

import com.src.exception.assignment.AssignmentNotFoundException;
import com.src.exception.assignment.NoSeatsAvailableException;
import com.src.exception.skill.SkillNotFoundException;
import com.src.exception.user.UserNotFoundException;
import com.src.exception.user.UsernameAlreadyExistException;
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
import java.util.Optional;
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

    public UserResponse register(UserRequest user) throws UsernameAlreadyExistException {

        UserEntity userEntity = new UserEntity();

        if(validUsername(user.getUsername())){
            userEntity.setUsername(user.getUsername());
        }
        else {
            throw new UsernameAlreadyExistException("Email provided already exist.");
        }
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userEntity.setFirstname(user.getFirstName());
        userEntity.setLastname(user.getLastName());
        userEntity.setYears(user.getYears());
        userEntity.setRole(user.getRole());
        UserEntity createdUser = userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserid(createdUser.getUserId());
        userResponse.setUsername(createdUser.getUsername());
        userResponse.setPassword(createdUser.getPassword());
        userResponse.setRole(createdUser.getRole());
        userResponse.setFirstname(userEntity.getFirstname());
        userResponse.setLastname(userEntity.getLastname());
        userResponse.setYears(String.valueOf(userEntity.getYears()));
        return userResponse;
    }

    public UserResponse updateUser(UpdateUserProfileRequest userProfileRequest) throws UserNotFoundException, SkillNotFoundException, AssignmentNotFoundException, NoSeatsAvailableException {

        List<UserAssignmentDetailResponse> assignmentDetailResponse;
        List<SkillResponse> skiillList;

        UserResponse userResponse = new UserResponse();

        UserEntity userEntity = userRepository.findByUserId(userProfileRequest.getUserid());

        if (userEntity == null) {
            throw new UserNotFoundException("Invalid User");
        } else {
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
            userResponse.setRole(userEntity.getRole());
            userResponse.setActiveAssignment(assignmentDetailResponse);
            userResponse.setSkillList(skiillList);
        }
        return userResponse;
    }


    public List<SkillEntity> addSkillsToUser(UserEntity user, List<Long> skills) throws SkillNotFoundException {
        List<SkillEntity> skillList = new ArrayList<>();
        if(skills != null) {
            List<Long> uniqueSkills = skills.stream().distinct().collect(Collectors.toList());
            skillList = skillRepository.findBySkillIdIn(uniqueSkills);
            if (skillList.size() != uniqueSkills.size()) {
                throw new SkillNotFoundException("Skill/s you provided not Exist");
            }
            else {
                List<UserSkillEntity> skillEntityList = userSkillRepository.findByUser(user);
                if (skillEntityList.size() > 0) {
                    skillEntityList.forEach(ex_skills -> {
                        userSkillRepository.delete(ex_skills);
                    });
                }
                skillList.forEach((skill) -> {
                    UserSkillEntity userSkillEntity = new UserSkillEntity();
                    userSkillEntity.setUser(user);
                    userSkillEntity.setSkill(skill);
                    userSkillRepository.save(userSkillEntity);
                });
                return skillList;
            }
        }
        else {
            return skillList;
        }
    }

    public  List<SkillResponse> addSkillsToExitingUser(UpdateUserProfileRequest userSkillRequest) throws SkillNotFoundException, UserNotFoundException {

        UserEntity userEntity = userRepository.findByUserId(userSkillRequest.getUserid());
        List<SkillResponse> skiillList = new ArrayList<>();
        if (userEntity == null) {
            throw new UserNotFoundException("Invalid User");
        } else {
            List<SkillEntity> userSkills = addSkillsToUser(userEntity, userSkillRequest.getSkillId());
                if (userSkills != null) {
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

    public List<UserAssignmentResultsDto> addActiveAssignment(UserEntity user, Long activeAssignmentId,String status) throws AssignmentNotFoundException, NoSeatsAvailableException {

            if(status.equals("No")) {
                userAssignmentRepository.deactivateOtherAssignmentOfUser(user.getUserId());
                return assignmentRepository.getUserAssignmentDetails(user.getUserId());
            } else if (status.equals("New") && activeAssignmentId > 0) {
                userAssignmentRepository.deactivateOtherAssignmentOfUser(user.getUserId());
                Optional<AssignmentEntity> assignment = assignmentRepository.findByAssignmentId(activeAssignmentId);
                if (!assignment.isPresent()) {
                    throw new AssignmentNotFoundException("Assignment is not found");
                }
                else {
                    int availableSeats = assignment.get().getSeats();
                    if (availableSeats > 0) {
                        UserAssignmentEntity assignmentEntity = new UserAssignmentEntity();
                        assignmentEntity.setUser(user);
                        assignmentEntity.setAssignment(assignment.get());
                        assignmentEntity.setActive(true);
                        userAssignmentRepository.save(assignmentEntity);
                        assignment.get().setSeats(availableSeats - 1);
                        assignmentRepository.save(assignment.get());
                    } else {
                        throw new NoSeatsAvailableException("There are no seats available in this assignment for this user.");
                    }
                }
                return assignmentRepository.getUserAssignmentDetails(user.getUserId());
            }else {
                return assignmentRepository.getUserAssignmentDetails(user.getUserId());
            }

    }

    public  List<UserAssignmentDetailResponse> updateUsersAssignment(UpdateUserProfileRequest updateUserAssignmentRequest) throws UserNotFoundException, AssignmentNotFoundException, NoSeatsAvailableException {

        UserEntity user = userRepository.findByUserId(updateUserAssignmentRequest.getUserid());
        if(user == null){
            throw new UserNotFoundException("Invalid User");
        }
        else{
            List<UserAssignmentResultsDto> usersAllAssignments = addActiveAssignment(user,updateUserAssignmentRequest.getActiveAssignmentId(), updateUserAssignmentRequest.assignmentStatus);
            List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
            if(usersAllAssignments != null) {
                usersAllAssignments.forEach(assignments -> {
                    UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
                    detailResponse.setActiveAssignmentId(assignments.getAssignmentId());
                    detailResponse.setCompanyName(assignments.getCompanyName());
                    detailResponse.setPosition(assignments.getPosition());
                    detailResponse.setStatus(assignments.getIsActive());
                    assignmentDetailResponse.add(detailResponse);
                });
            }
           return assignmentDetailResponse;
        }


    }

    /*
    * This service only provide users initial details except skills and Assignments for the search user.
    * */
    public List<UserResponse> getAllUsers(){

        List<UserResponse> allUsers = new ArrayList<>();

        List<UserEntity> users = userRepository.findAllUsers();

        users.forEach(user -> {
           UserResponse userResponse = new UserResponse();
           userResponse.setUserid(user.getUserId());
           userResponse.setFirstname(user.getFirstname());
           userResponse.setLastname(user.getLastname());
           userResponse.setUsername(user.getUsername());
           userResponse.setPassword(user.getPassword());
           userResponse.setRole(user.getRole());
           userResponse.setYears(String.valueOf(user.getYears()));
           allUsers.add(userResponse);
        });
        return  allUsers;
    }

    public UserResponse getUser(Long userId){

        UserEntity user = userRepository.findByUserId(userId);

        List<SkillEntity> userSkills = userRepository.FindUserSkills(user.getUserId());
        List<SkillResponse> skiillList = new ArrayList<>();
        if (userSkills !=null)
        userSkills.forEach((skill) -> {
            SkillResponse skillEach = new SkillResponse();
            skillEach.setSkillId(skill.getSkillId());
            skillEach.setSkillSdesc(skill.getSkillSdesc());
            skillEach.setSkillLdesc(skill.getSkillLdesc());
            skiillList.add(skillEach);
        });

        List<UserAssignmentResultsDto> usersAllAssignments = assignmentRepository.getUserAssignmentDetails(user.getUserId());
        List<UserAssignmentDetailResponse> assignmentDetailResponse = new ArrayList<>();
        if( usersAllAssignments != null)
        usersAllAssignments.forEach(assignments -> {
            UserAssignmentDetailResponse detailResponse = new UserAssignmentDetailResponse();
            detailResponse.setUniqueAssignmentId(assignments.getUniqueId());
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
        response.setPassword(user.getPassword());
        response.setRole(user.getRole());
        response.setYears(String.valueOf(user.getYears()));
        response.setActiveAssignment(assignmentDetailResponse);
        response.setSkillList(skiillList);


        return response;
    }

    public boolean validUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return false;
        }
        else {
            return true;
        }
    }

    public  boolean hasActiveAssignment(long userId){

        Optional<List<UserAssignmentEntity>> activeAssignments = userAssignmentRepository.findUsersHasActiveAssignment(userId);
        if(activeAssignments.get().size()>0){
            return true;
        }else{
            return false;
        }
    }

}
