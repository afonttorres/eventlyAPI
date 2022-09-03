package com.example.evently.dto.event.req;

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
    String type;
    String[] participants;
}
