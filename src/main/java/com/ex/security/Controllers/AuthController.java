package com.ex.security.Controllers;

import com.ex.security.Services.AuthService;
import com.ex.security.persistence.Entity.User;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*")
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

    @GetMapping("/download")
    public ResponseEntity downloadFileFromLocal(@RequestParam("fileName") String fileName) {
        Path path = Paths.get( fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
