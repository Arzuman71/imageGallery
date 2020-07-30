package com.imagegallery.imagegallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ImageGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageGalleryApplication.class, args);
    }

}
