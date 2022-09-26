package com.example.evently.dto.event.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReq {
    @NotBlank(message="Title is mandatory!")
    @Size(min = 3, max = 300, message = "Title should have 2 to 300 characters!")
    private String title;
    @NotBlank(message="Description is mandatory!")
    @Size(min = 3, max = 2500, message = "Description should have 2 to 2500 characters!")
    private String description;
    @NotBlank(message="Type can't be empty!")
    @Size(min = 3, max = 15, message = "Type should have 2 to 15 characters!")
    private String type;
    @NotNull(message="Date can't be empty!")
    private LocalDateTime date;
}
