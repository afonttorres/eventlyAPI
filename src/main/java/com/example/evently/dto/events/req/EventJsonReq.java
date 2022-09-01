package com.example.evently.dto.events.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventJsonReq {
    String title;
    String description;
    String username;
    String[] categories;
    String[] participants;
}
