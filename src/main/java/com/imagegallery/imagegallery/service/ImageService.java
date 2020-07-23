package com.imagegallery.imagegallery.service;


import com.imagegallery.imagegallery.model.Image;
import com.imagegallery.imagegallery.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final ImageRepository imageRepository;


    public Image save(Image image, MultipartFile file) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(uploadDir, name);

            file.transferTo(picUrl);

        image.setPicUrl(name);
    } catch (IOException e) {
        e.printStackTrace();
    }
        return imageRepository.save(image);

    }

    public List<Image> findAllByCategoryId(int id) {
        return imageRepository.findAllByCategory_Id(id) ;
    }

    public List<Image> findAll() {
        return imageRepository.findAll() ;
    }



    public byte[] getImage(String imageName) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }

}

