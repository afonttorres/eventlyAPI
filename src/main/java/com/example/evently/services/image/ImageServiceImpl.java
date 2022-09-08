package com.example.evently.services.image;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.output.Message;
import com.example.evently.dto.image.ImageReqDelete;
import com.example.evently.dto.image.ImageRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.ImageMapper;
import com.example.evently.models.Image;
import com.example.evently.models.User;
import com.example.evently.repositories.ImageRepository;
import com.example.evently.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{

    ImageRepository imageRepository;
    CloudinaryService cloudinaryService;
    EventService eventService;
    AuthFacade authFacade;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository,
                            CloudinaryService cloudinaryService,
                            EventService eventService,
                            AuthFacade authFacade) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
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
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can upload images!", "I-001");
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if(bufferedImage == null) throw new BadReqEx("Invalid Image", "I-002");
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new ImageMapper().mapCloudinaryResultToImage(result, event);
        imageRepository.save(image);
        return new ImageRes("Image uploaded!", image.getImgUrl(), image.getId());
    }

    @Override
    public Message deleteById(Long id) throws IOException {
        Image image = this.findById(id);
        if(image.getEvent().getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can delete its images!", "I-001");
        Map result = cloudinaryService.delete(image.getImgId());
        //validar amb result?
        imageRepository.delete(image);
        return new Message("Image deleted!");
    }

    @Override
    public Message deleteByUrl(ImageReqDelete req) throws IOException {
        Image image = this.findByUrl(req.getUrl());
        if(image.getEvent().getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can delete its images!", "I-001");
        cloudinaryService.delete(image.getImgId());
        imageRepository.delete(image);
        return new Message("Image deleted!");
    }




}
