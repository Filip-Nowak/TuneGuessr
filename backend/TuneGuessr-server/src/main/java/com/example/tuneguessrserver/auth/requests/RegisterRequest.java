package com.example.tuneguessrserver.auth.requests;

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
    @NotBlank(message = "2")
    @Length(min = 3, max = 20, message = "3")
    private String nickname;
    @NotBlank(message = "4")
    @Email(message = "5")
    private String email;
    @NotBlank(message = "6")
    @Length(min = 6, max = 40, message = "7")
    private String password;
}
