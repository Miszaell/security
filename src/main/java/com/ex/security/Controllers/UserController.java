package com.ex.security.Controllers;

import com.ex.security.Services.UserService;
import com.ex.security.persistence.DTO.UserDTO;
import com.ex.security.persistence.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(){
        return this.userService.findAll();
    }

    @GetMapping("/byId")
    @ResponseBody
    public User findById(@RequestParam("id") Long id) {
        return this.userService.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO){
        return this.userService.createUser(userDTO);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@RequestParam("id") Long id, @RequestParam("password") String password){
        this.userService.updateUserPassword(id, password);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-name")
    public ResponseEntity<Void> updateUserName(@RequestParam("id") Long id, @RequestParam("name") String name){
        this.userService.updateUserName(id, name);

        return ResponseEntity.noContent().build();
    }
}
