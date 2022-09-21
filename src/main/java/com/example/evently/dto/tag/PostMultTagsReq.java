package com.example.evently.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMultTagsReq {
    @NotNull(message = "Tags can't be empty!")
    @Size(min = 1, message = "Tags should contain at least one tag!")
    String[] tags;
}
