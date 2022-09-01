package com.example.evently.dto.events.res;
import com.example.evently.dto.user.res.NestedUser;
import com.example.evently.models.Category;
import com.example.evently.models.Participation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRes {
    private Long id;
    private String title;
    private String description;
    private NestedUser publisher;
    private Set<Category> categories;
    private Set<Participation> participants;
    private int participantsCount;
}
