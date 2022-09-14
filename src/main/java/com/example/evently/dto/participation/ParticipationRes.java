package com.example.evently.dto.participation;

import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.event.res.NestedEventRes;
import com.example.evently.dto.user.res.NestedUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRes {
    Long id;
    NestedEventRes event;
    NestedUser participant;
}
