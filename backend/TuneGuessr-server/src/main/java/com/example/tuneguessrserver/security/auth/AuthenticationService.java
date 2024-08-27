package com.example.tuneguessrserver.security.auth;

import com.example.tuneguessrserver.entity.ConfirmationToken;
import com.example.tuneguessrserver.entity.ResetPasswordToken;
import com.example.tuneguessrserver.entity.User;
import com.example.tuneguessrserver.entity.UserProfile;
import com.example.tuneguessrserver.enums.AuthStatus;
import com.example.tuneguessrserver.model.NewPasswordRequest;
import com.example.tuneguessrserver.model.PasswordResetRequest;
import com.example.tuneguessrserver.repository.*;
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
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Transactional
    public void register(RegisterRequest request,boolean v) throws AuthError {
        AuthError error = new AuthError(new LinkedList<>());
        if (userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            if (user.isVerified()) {
                error.addError(AuthStatus.EMAIL_EXISTS);
            } else {
                updateUnverifiedUser(user);
            }
        }
        if (userProfileRepository.existsByNickname(request.getNickname())) {
            error.addError(AuthStatus.NICKNAME_EXISTS);
        }
        if (!error.getErrors().isEmpty()) {

            throw error;
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleRepository.findByName("USER")))
                .build();
        UserProfile profile = UserProfile.builder()
                .nickname(request.getNickname())
                .user(user)
                .build();
        String token = UUID.randomUUID().toString();
        user.setProfile(profile);
        user = userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                user.getId()
        );
        confirmationTokenRepository.save(confirmationToken);
        if(!v)
            emailSender.sendEmail(request.getEmail(), "Confirm your email", "confirm your email: http://localhost:8080/api/auth/confirm?token=" + token);
    }
    @Transactional
    public void register(RegisterRequest request) throws AuthError {
        register(request,false);
    }
    @Transactional
    public String authenticate(AuthenticationRequest request) throws AuthError {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            return jwtService.generateToken(user);

        } catch (Exception e) {
            throw new AuthError(List.of(AuthStatus.INVALID_CREDENTIALS));
        }
    }
    @Transactional
    public void confirmToken(String token) throws AuthError {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken == null) {
            throw new AuthError(List.of(AuthStatus.INVALID_TOKEN));
        }
        if (confirmationToken.getConfirmedAt() != null) {
            throw new AuthError(List.of(AuthStatus.TOKEN_ALREADY_CONFIRMED));
        }
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AuthError(List.of(AuthStatus.TOKEN_EXPIRED));
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
        User user = userRepository.findById(confirmationToken.getUserId()).orElseThrow();
        user.setVerified(true);
        userRepository.save(user);
    }

    private void updateUnverifiedUser(User user) {
        userRepository.delete(user);
        List<ConfirmationToken> tokens = confirmationTokenRepository.findByUserId(user.getId());
        for (ConfirmationToken token : tokens) {
            token.setExpiresAt(LocalDateTime.now());
            confirmationTokenRepository.save(token);
        }
    }

    public void resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        resetPasswordTokenRepository.save(resetPasswordToken);
        emailSender.sendEmail(request.getEmail(), "Reset your password", "reset your password: http://localhost:8080/api/auth/reset?token=" + token);

    }


    public void checkToken(String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken == null) {
            throw new AuthError(List.of(AuthStatus.PASSWORD_RESET_TOKEN_NOT_FOUND));
        }
        if (resetPasswordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthError(List.of(AuthStatus.PASSWORD_RESET_TOKEN_EXPIRED));
        }
    }

    public void newPassword(NewPasswordRequest request,String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken == null) {
            throw new AuthError(List.of(AuthStatus.PASSWORD_RESET_TOKEN_NOT_FOUND));
        }
        if (resetPasswordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthError(List.of(AuthStatus.PASSWORD_RESET_TOKEN_EXPIRED));
        }
        User user = resetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        resetPasswordTokenRepository.delete(resetPasswordToken);
    }
}
