package com.example.evently.mappers;

import com.example.evently.dto.user.res.NestedUser;
import com.example.evently.models.user.User;

public class UserMapper {
    public NestedUser mapUserToNestedUser(User user){
        var res = new NestedUser();
        res.setCompleteName(user.getCompleteName());
        res.setUsername(user.getUsername());
        //avatar
        return res;
    }
}
