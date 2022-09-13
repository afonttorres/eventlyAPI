package com.example.evently.mappers;

import com.example.evently.models.Image;
import com.example.evently.models.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageMapper {
    public Image mapCloudinaryResultToImage(Map result, Event event){
        Image image = new Image();
        image.setName(result.get("original_filename").toString());
        image.setImgUrl( result.get("url").toString());
        image.setImgId(result.get("public_id").toString());
        image.setEvent(event);
        return image;
    }

    public String[] mapImagesToArray(List<Image> images){
        var res = new ArrayList<>();
        if(images == null) return res.toArray(new String[0]);
        images.forEach(i -> res.add(i.getImgUrl()));
        return res.toArray(new String[0]);
    }
}
