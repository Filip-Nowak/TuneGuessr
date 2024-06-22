package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.repository.RoleRepository;
import com.example.tuneguessrserver.repository.UserProfileRepository;
import com.example.tuneguessrserver.repository.UserRepository;
import com.example.tuneguessrserver.security.JwtService;
import com.example.tuneguessrserver.security.SimpleGrantedAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;
    public AuthenticationResponse register(RegisterRequest request) throws RuntimeException{
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }else if(userProfileRepository.existsByNickname(request.getNickname())){
            throw new RuntimeException("Nickname already exists");
        }
        var user= User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleRepository.findByName("USER")))
                .build();
        UserProfile profile=UserProfile.builder()
                .nickname(request.getNickname())
                .user(user)
                .build();
        userProfileRepository.save(profile);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if(!userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User not found");
        }
        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        }catch (Exception e){
            System.out.println("exception");
            throw new RuntimeException("Invalid credentials");
        }
        var user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
