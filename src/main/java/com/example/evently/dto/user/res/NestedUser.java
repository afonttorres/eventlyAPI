package com.example.evently.dto.user.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestedUser {
    private String completeName;
    private String username;
    private String avatar = "https://i.pinimg.com/474x/b7/cf/46/b7cf46c96e503fdec995645e70d95705.jpg";
}
