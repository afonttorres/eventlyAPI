package com.example.evently.dto.event.req;

import com.example.evently.models.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventJsonReq {
    String title;
    String description;
    String username;
    String[] tags;
    String img;
    Integer type;
    String[] participants;
}
