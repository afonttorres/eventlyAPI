package com.example.evently.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagReq {
    @NotBlank(message = "Tag is mandatory!")
    @Size(min = 2, max = 50, message = "Tag should have 2 to 50 characters!")
    String name;
}
