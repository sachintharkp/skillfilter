package com.src.service.auth;

import com.src.exception.user.InvalidPasswordException;
import com.src.exception.user.InvalidUsernameException;
import com.src.models.auth.*;
import com.src.models.user.UserEntity;
import com.src.repositories.auth.LoginHistRepository;
import com.src.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    private LoginHistRepository loginHistRepository;

    @Autowired
    private UserRepository userRepository;


    public LoginResponse login(LoginRequest loginRequest) throws InvalidUsernameException, InvalidPasswordException {
        LoginResponse loginResponse = new LoginResponse();
        Optional<UserEntity> username = userRepository.findByUsername(loginRequest.getUsername());
        if (username.isPresent()) {
            Optional<UserEntity> user = userRepository.findOneByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
            if(user.isPresent()){

                UUID randomToken = UUID.randomUUID();

                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setUserId(user.get().getUserId());
                loginHistory.setActive(true);
                loginHistory.setLastLogged(new Date());
                loginHistRepository.save(loginHistory);

                loginResponse.setUserid(user.get().getUserId());
                loginResponse.setStatus(SUCCESS);
                loginResponse.setRole(user.get().getRole());
            }
            else {
                throw new InvalidPasswordException("Password Incorrect");
            }
        }
        else {
            throw new InvalidUsernameException(loginRequest.getUsername() + " Not exist.");
        }

        return loginResponse;
    }

    public LogoutResponse logout(LogoutRequest logoutRequest){

        LogoutResponse logoutResponse = new LogoutResponse();
            Optional<List<LoginHistory>> loggedSessions = loginHistRepository.findByUserId(logoutRequest.getUserid());
            if(loggedSessions.get().size()>0){
                loggedSessions.get().forEach(loginHistory -> {
                    loginHistRepository.deactivateUser(logoutRequest.getUserid());
                });
                logoutResponse.setStatus(SUCCESS);
           }
           else{
                logoutResponse.setStatus(FAIL);
            }
        return logoutResponse;
    }
}
