package com.example.tuneguessrserver.auth.requests;

import com.example.tuneguessrserver.response.status.AuthStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NewPasswordRequest {
    @NotBlank(message = ""+ AuthStatus.PASSWORD_REQUIRED)
    @Length(min = 6, max = 40, message = ""+ AuthStatus.PASSWORD_LENGTH)
    private String password;
}
