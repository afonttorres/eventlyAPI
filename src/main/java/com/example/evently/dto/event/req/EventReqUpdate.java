package com.example.evently.dto.event.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReqUpdate {
    private String title;
    private String description;
    private String type;
    private Date date;
}
