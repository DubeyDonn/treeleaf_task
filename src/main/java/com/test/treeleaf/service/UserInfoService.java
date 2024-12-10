package com.test.treeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.treeleaf.model.UserInfo;
import com.test.treeleaf.repository.UserRepository;
import com.test.treeleaf.security.UserInfoDetails;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository, @Lazy PasswordEncoder encoder) {
        this.repository = userInfoRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        UserInfoDetails userInfoDetails = userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return userInfoDetails;
    }

    public String addUser(UserInfo userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Added Successfully";
    }

    public UserInfo getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public UserInfo getUserByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }
}