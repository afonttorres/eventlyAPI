package com.example.evently.mappers;

import com.example.evently.models.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMapper {
    public String[] mapMultTagsToStringArr(List<Tag> tags){
        var res = new ArrayList<>();
        tags.forEach(t -> res.add(t.getName()));
        return res.toArray(new String[0]);
    }
}
