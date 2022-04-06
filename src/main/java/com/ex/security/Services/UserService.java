package com.ex.security.Services;

import com.ex.security.Exceptions.UserExceptions;
import com.ex.security.Mapper.UserDTOtoUser;
import com.ex.security.persistence.DTO.UserDTO;
import com.ex.security.persistence.Entity.User;
import com.ex.security.persistence.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserDTOtoUser mapper;

    public UserService(UserRepository repo, UserDTOtoUser mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public User createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO);

        return this.repo.save(user);
    }

    public List<User> findAll(){
        return this.repo.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = this.repo.findById(id);

        if (user.isEmpty()) {
            throw new UserExceptions("User not found", HttpStatus.NOT_FOUND);
        }

        User usr = user.get();

        return usr;
    }

    public void updateUserName(Long id, String name) {
        Optional<User> usr = this.repo.findById(id);
        if (usr.isEmpty()) {
            throw new UserExceptions("User not found", HttpStatus.NOT_FOUND);
        }

        this.repo.updateUserName(id, name);
    }

    public void updateUserPassword(Long id, String password) {
        Optional<User> usr = this.repo.findById(id);
        if (usr.isEmpty()) {
            throw new UserExceptions("User not found", HttpStatus.NOT_FOUND);
        }

        this.repo.updateUserPassword(id, password);
    }
}
