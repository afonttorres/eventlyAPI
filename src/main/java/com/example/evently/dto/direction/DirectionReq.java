package com.example.evently.dto.direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionReq {
    @NotBlank(message = "Country is mandatory!")
    @Size(min = 3, max = 300, message = "Country should have 3 to 300 characters!")
    String country;
    @NotBlank(message = "Province is mandatory!")
    @Size(min = 3, max = 300, message = "Province should have 3 to 300 characters!")
    String province;
    @NotBlank(message = "City is mandatory!")
    @Size(min = 3, max = 300, message = "City should have 3 to 300 characters!")
    String city;
    @NotBlank(message = "Street is mandatory!")
    @Size(min = 3, max = 300, message = "Street should have 3 to 300 characters!")
    String street;
    @NotBlank(message = "Building is mandatory!")
    @Size(min = 1, max = 12, message = "Building should have at least 1 character!")
    String building;
    String door;
}
