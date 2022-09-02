package com.example.evently.dto.event.res;
import com.example.evently.dto.user.res.NestedUser;
import com.example.evently.models.EventType;
import com.example.evently.models.Tag;
import com.example.evently.models.Participation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRes {
    private Long id;
    private String title;
    private String description;
    private NestedUser publisher;
    private List<Tag> tags;
    private String type;
    private List<Participation> participants;
    private int participantsCount;
}
