package com.example.tuneguessrserver.challenge.requests;

import com.example.tuneguessrserver.response.status.ApiErrorStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class CreateChallengeModel {
    @NotBlank(message = "" + ApiErrorStatus.CHALLENGE_NAME_REQUIRED)
    @Length(max = 40, message = "" + ApiErrorStatus.CHALLENGE_NAME_LENGTH)
    private String name;
    @Length( max = 200, message = "" + ApiErrorStatus.CHALLENGE_DESCRIPTION_LENGTH)
    private String description;
}
