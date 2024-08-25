package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.entity.ConfirmationToken;
import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.repository.ConfirmationTokenRepository;
import com.example.tuneguessrserver.repository.RoleRepository;
import com.example.tuneguessrserver.repository.UserProfileRepository;
import com.example.tuneguessrserver.repository.UserRepository;
import com.example.tuneguessrserver.security.JwtService;
import com.example.tuneguessrserver.security.SimpleGrantedAuthority;
import com.example.tuneguessrserver.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSender emailSender;
    public void register(RegisterRequest request) throws RuntimeException{
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
        String token = UUID.randomUUID().toString();
        user =userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                user.getId()
        );
        userProfileRepository.save(profile);
        confirmationTokenRepository.save(confirmationToken);
        emailSender.sendEmail(request.getEmail(),"Confirm your email","confirm your email: http://localhost:8080/api/auth/confirm?token="+token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void confirmToken(String token) throws RuntimeException{
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if(confirmationToken==null){
            throw new RuntimeException("Invalid token");
        }
        if(confirmationToken.getConfirmedAt()!=null){
            throw new RuntimeException("Token already confirmed");
        }
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
        User user = userRepository.findById(confirmationToken.getUserId()).orElseThrow();
        user.setVerified(true);
        userRepository.save(user);
    }
}
