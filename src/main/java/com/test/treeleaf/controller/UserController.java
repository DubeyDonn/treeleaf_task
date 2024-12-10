package com.test.treeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.test.treeleaf.model.UserInfo;
import com.test.treeleaf.model.enums.Role;
import com.test.treeleaf.model.request.AuthRequest;
import com.test.treeleaf.service.JwtService;
import com.test.treeleaf.service.UserInfoService;

@RestController
public class UserController {

    private final UserInfoService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserInfoService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/register")
    public String registerUser(@RequestBody UserInfo userInfo) {
        userInfo.setRole(Role.USER);
        return userService.addUser(userInfo);
    }

    @PostMapping("/admin/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String registerAdmin(@RequestBody UserInfo userInfo) {
        userInfo.setRole(Role.ADMIN);
        return userService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}