package com.imagegallery.imagegallery.service;


import com.imagegallery.imagegallery.model.MainImage;
import com.imagegallery.imagegallery.repository.MainImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainImageService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final MainImageRepository mainImageRepository;

    public MainImage getOne(int id) {
        return mainImageRepository.getOne(id);
    }

    public void save(MultipartFile file, MainImage mainImage) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(uploadDir, name);

            file.transferTo(picUrl);

            mainImage.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainImageRepository.save(mainImage);

    }

}
