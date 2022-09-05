package com.example.evently.dto.requirement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequirementReq {
    @NotBlank
    @Size(min = 2, max = 50, message = "Requirement should have 2 to 50 characters!")
    String name;
    @NotNull
    Long eventId;
}
