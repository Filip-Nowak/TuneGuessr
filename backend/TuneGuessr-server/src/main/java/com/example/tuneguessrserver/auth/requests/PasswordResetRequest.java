package com.example.tuneguessrserver.auth.requests;

import com.example.tuneguessrserver.response.status.AuthStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PasswordResetRequest {
    @NotBlank(message = ""+ AuthStatus.EMAIL_REQUIRED)
    @Email(message = ""+ AuthStatus.EMAIL_FORMAT)
    private String email;
}
