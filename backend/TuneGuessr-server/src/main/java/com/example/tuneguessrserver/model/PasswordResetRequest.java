package com.example.tuneguessrserver.model;

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
    @NotBlank(message = "4")
    @Email(message = "5")
    private String email;
}
