package com.example.tuneguessrserver.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Nickname is required")
    @Length(min = 3, max = 20, message = "Nickname must be between 3 and 20 characters")
    private String nickname;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password is required")
    @Length(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;
}
