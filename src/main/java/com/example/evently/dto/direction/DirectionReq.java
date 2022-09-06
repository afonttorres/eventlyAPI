package com.example.evently.dto.direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionReq {
    @NotBlank
    String country;
    String province;
    @NotBlank
    String city;
    @NotBlank
    String street;
    String building;
    String door;
    @NotBlank
    Long eventId;
}
