package com.evdictionaries.service.impl;

import com.evdictionaries.error.CustomErrorException;
import com.evdictionaries.models.User;
import com.evdictionaries.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void updateResetPasswordToken(String token,String email)throws CustomErrorException{
        User user = userRepository.findByEmail(email);
        if (user != null){
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }else {
            throw new CustomErrorException("Could not find any user with email "+email);
        }
    }

    public User get(String resetPasswordToken){
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user,String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
