package com.ex.security.Services;

import com.ex.security.Exceptions.UserExceptions;
import com.ex.security.persistence.Entity.User;
import com.ex.security.persistence.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User login(String matricula, String password) {
        Optional<User> user = this.userRepo.login(matricula, password);

        if (user.isEmpty()){
            throw new UserExceptions("User not found", HttpStatus.NOT_FOUND);
        }
        User usr = user.get();
         String publicDirectory = "src/main/resources/Keys/Public/"+usr.getNombre()+"_"+usr.getMatricula();
         String privateDirectory = "src/main/resources/Keys/Private/"+usr.getNombre()+"_"+usr.getMatricula();


//        try {
//            generateKeyPair(publicDirectory, privateDirectory);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String encr = encrypt(usr.getNombre(),publicDirectory+"_public.enc");
//            System.out.println(encr);
//            System.out.println(decrypt(encr,privateDirectory+"_private.enc"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return usr;
    }


}
