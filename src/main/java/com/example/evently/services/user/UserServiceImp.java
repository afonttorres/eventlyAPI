package com.example.evently.services.user;

import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.user.User;
import com.example.evently.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id){
        var user = userRepository.findById(id);
        if(user.isEmpty()) throw new NotFoundEx("User not found", "U-404");
        return user.get();
    }

}
