package com.example.tuneguessrserver.challenge.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class CreateChallengeModel {
    @NotBlank(message = "Name is required")
    @Length(max = 40, message = "Name must be less than 40 characters")
    private String name;
    @Length( max = 200, message = "Description must be less than 200 characters")
    private String description;
}
