package com.example.evently.mappers;

import com.example.evently.models.Type;

public class TypeMapper {
    public Type stringToType(String type){
        if(type.equals("online")) return Type.ONLINE;
        return Type.OFFLINE;
    }
}
