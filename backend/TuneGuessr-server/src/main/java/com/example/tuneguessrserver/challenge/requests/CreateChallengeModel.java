package com.example.tuneguessrserver.challenge.requests;

import com.example.tuneguessrserver.response.status.ApiStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class CreateChallengeModel {
    @NotBlank(message = "" + ApiStatus.CHALLENGE_NAME_REQUIRED)
    @Length(max = 40, message = "" + ApiStatus.CHALLENGE_NAME_LENGTH)
    private String name;
    @Length( max = 200, message = "" + ApiStatus.CHALLENGE_DESCRIPTION_LENGTH)
    private String description;
}
