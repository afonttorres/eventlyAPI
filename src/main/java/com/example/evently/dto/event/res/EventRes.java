package com.example.evently.dto.event.res;
import com.example.evently.dto.user.res.NestedUser;
import com.example.evently.models.Requirement;
import com.example.evently.models.Tag;
import com.example.evently.models.Participation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRes {
    private Long id;
    private String title;
    private String description;
    private NestedUser publisher;
    private String date;
    private String hour;
    private String[] tags;
    private String[] requirements;
    private String type;
    private int participantsCount;
    private String[] images;
    private String location = "";
    private boolean isParticipant = false;
}
