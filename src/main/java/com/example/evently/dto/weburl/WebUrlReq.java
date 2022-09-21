package com.example.evently.dto.weburl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebUrlReq {
    @NotBlank(message = "Url is mandatory!")
    @Size(min=7, message = "Url should have at least 7 characters!")
    String url;
}
