package com.ex.security.Controllers;

import com.ex.security.Services.AuthService;
import com.ex.security.persistence.Entity.User;
import netscape.javascript.JSObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("192.168.0.200:8080")
@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("matricula") String matricula, @RequestParam("password") String password){
        User user = this.authService.login(matricula, password);


        return ResponseEntity.accepted().body(user);
    }
}
