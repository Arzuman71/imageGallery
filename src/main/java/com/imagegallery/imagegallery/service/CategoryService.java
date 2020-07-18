package com.imagegallery.imagegallery.service;



import com.imagegallery.imagegallery.model.Category;
import com.imagegallery.imagegallery.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Value("${file.upload.dir}")
    private String uploadDir;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }



    public void save(Category category, MultipartFile file) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File image = new File(uploadDir, name);
            file.transferTo(image);
            category.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryRepository.save(category);
    }





}
