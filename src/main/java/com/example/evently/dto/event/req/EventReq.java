package com.example.evently.dto.event.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReq {
    private String title;
    private String description;
    private String type;
    private Date date;
//    private Time hour;
}
