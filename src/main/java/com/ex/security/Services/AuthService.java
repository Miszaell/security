package com.ex.security.Services;

import com.ex.security.Exceptions.UserExceptions;
import com.ex.security.persistence.Entity.User;
import com.ex.security.persistence.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User login(String matricula, String password){
        Optional<User> user = this.userRepo.login(matricula, password);

        if (user.isEmpty()){
            throw new UserExceptions("User not found", HttpStatus.NOT_FOUND);
        }

        User usr = user.get();
        return usr;
    }
}
