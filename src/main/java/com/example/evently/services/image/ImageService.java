package com.example.evently.services.image;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.image.ImageReqDelete;
import com.example.evently.dto.image.ImageRes;
import com.example.evently.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> getAllImages();
    boolean exists(Long id);
    Image findById(Long id);

    ImageRes upload(MultipartFile multipartFile, Long id) throws IOException;

    Message deleteById(Long id) throws IOException;

    Message deleteByUrl(ImageReqDelete req) throws IOException;
}
