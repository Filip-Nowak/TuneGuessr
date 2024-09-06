package com.example.tuneguessrserver.auth.requests;

import com.example.tuneguessrserver.response.status.AuthStatus;
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
    @NotBlank(message = ""+ AuthStatus.NICKNAME_REQUIRED)
    @Length(min = 3, max = 20, message = ""+ AuthStatus.NICKNAME_LENGTH)
    private String nickname;
    @NotBlank(message = ""+ AuthStatus.EMAIL_REQUIRED)
    @Email(message = ""+ AuthStatus.EMAIL_FORMAT)
    private String email;
    @NotBlank(message = ""+ AuthStatus.PASSWORD_REQUIRED)
    @Length(min = 6, max = 40, message = ""+ AuthStatus.PASSWORD_LENGTH)
    private String password;
}
