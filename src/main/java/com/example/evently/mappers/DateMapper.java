package com.example.evently.mappers;

import java.text.SimpleDateFormat;
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

    public String convertDateToStringDate(Date date){
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String convertDateToStringHour(Date date){
        return new SimpleDateFormat("HH:mm").format(date.getTime());
    }
}
