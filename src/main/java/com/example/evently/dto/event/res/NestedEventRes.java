package com.example.evently.dto.event.res;

import com.example.evently.dto.user.res.NestedUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestedEventRes {
    private Long id;
    private String title;
    private String description;
    private NestedUser publisher;
    private String date;
    private String hour;
    private String type;
    private int participantsCount;
    private String[] images;
    private String location = "";
}
