package com.example.evently.services.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    Cloudinary cloudinary;
    private Map<String , String> valuesMap = new HashMap<>();

    public CloudinaryServiceImpl() {
        valuesMap.put("cloud_name", "dkju5on5v");
        valuesMap.put("api_key", "575439795163737");
        valuesMap.put("api_secret", "UUs4OoPDUhyhK0Ade8N3fqIVzyM");
        cloudinary =  new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    public File convert(MultipartFile multipartFile) throws IOException {
        File  file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
