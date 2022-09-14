package com.example.evently.dto.event.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventJsonReq {
    String title;
    String description;
    String username;
    String[] tags;
    String [] requirements;
    Date date;
    String img;
    String type;
    String[] participants;
}
