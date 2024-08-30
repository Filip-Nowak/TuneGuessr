package com.example.tuneguessrserver.model;

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
    @NotBlank(message = "6")
    @Length(min = 6, max = 40, message = "7")
    private String password;
}
