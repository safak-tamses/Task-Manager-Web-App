package com.example.taskmanager.controller;

import com.example.taskmanager.entity.dao.AuthenticationResponse;
import com.example.taskmanager.entity.dto.LoginWithTokenResponse;
import com.example.taskmanager.entity.dto.UserLoginRequestDTO;
import com.example.taskmanager.entity.dto.UserRegisterRequestDTO;
import com.example.taskmanager.service.UserCRUDService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class HomeController {
    private UserCRUDService userCRUDService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> saveUser(@RequestBody UserRegisterRequestDTO user){
        return new ResponseEntity<>(userCRUDService.saveUser(user), HttpStatusCode.valueOf(200));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequestDTO user){
        return new ResponseEntity<>(userCRUDService.userLogin(user),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/test")
    public ResponseEntity test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(authentication,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/loginWithToken")
    public ResponseEntity<LoginWithTokenResponse> loginWithToken(@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(userCRUDService.loginWithToken(bearerToken), HttpStatusCode.valueOf(200));
    }
}
