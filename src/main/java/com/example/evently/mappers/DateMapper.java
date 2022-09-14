package com.example.evently.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateMapper {

    public LocalDateTime convertDateToLocalDate(Date date){
        return date.toInstant()
                .atZone(ZoneId.of("GMT+2"))
                .toLocalDateTime();
    }

    public Date convertLocalDateTimeToDate(LocalDateTime local){
        return Date.from(local.atZone(ZoneId.of("GMT+2")).toInstant());
    }
}
