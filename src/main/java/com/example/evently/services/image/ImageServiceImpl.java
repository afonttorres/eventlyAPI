package com.example.evently.services.image;

import com.example.evently.dto.image.CloudinaryMsg;
import com.example.evently.dto.image.ImageReqDelete;
import com.example.evently.dto.image.ImageRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.ImageMapper;
import com.example.evently.models.Image;
import com.example.evently.repositories.ImageRepository;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService{

    ImageRepository imageRepository;
    CloudinaryService cloudinaryService;

    EventService eventService;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CloudinaryService cloudinaryService, EventService eventService) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.eventService = eventService;
    }

    @Override
    public boolean exists(Long id) {
        return imageRepository.existsById(id);
    }


    @Override
    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    @Override
    public Image findById(Long id) {
        var image = imageRepository.findById(id);
        if(image.isEmpty()) throw new NotFoundEx("Image Not Found", "I-404");
        return image.get();
    }

    private Image findByUrl(String url){
        var image = imageRepository.findByImgUrl(url).stream().findFirst();
        if(image.isEmpty())
            throw new NotFoundEx("Image Not Found", "I-404");
        return image.get();
    }

    @Override
    public ImageRes upload(MultipartFile multipartFile, Long id) throws IOException {
        var event = eventService.getCompleteEventById(id);
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if(bufferedImage == null) throw new BadReqEx("Invalid Image", "I-001");
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new ImageMapper().mapCloudinaryResultToImage(result, event);
        imageRepository.save(image);
        return new ImageRes("Image Uploaded!", image.getImgUrl(), image.getId());
    }

    @Override
    public CloudinaryMsg deleteById(Long id) throws IOException {
        Image image = this.findById(id);
        Map result = cloudinaryService.delete(image.getImgId());
        //validar amb result?
        imageRepository.delete(image);
        return new CloudinaryMsg("Image deleted!");
    }

    @Override
    public CloudinaryMsg deleteByUrl(ImageReqDelete req) throws IOException {
        Image image = this.findByUrl(req.getUrl());
        cloudinaryService.delete(image.getImgId());
        imageRepository.delete(image);
        return new CloudinaryMsg("Image deleted!");
    }




}
