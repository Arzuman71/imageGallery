package com.imagegallery.imagegallery.controller;


import com.imagegallery.imagegallery.model.Image;
import com.imagegallery.imagegallery.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {


    private final ImageService imageService;

    @GetMapping("/byCategory")
    public String byCategoryId(ModelMap modelMap, @RequestParam(name = "id", required = false) Integer id) {

        if (id != null && id != 0) {
            List<Image> images = imageService.findAllByCategory_Id(id);
            if (images != null && images.size() != 0) {
                String categoryName = images.get(0).getCategory().getName();

                modelMap.addAttribute("categoryName", categoryName);
                modelMap.addAttribute("images", images);
            }
        } else {
            modelMap.addAttribute("categoryName", "All Category");
            modelMap.addAttribute("images", imageService.findAll());
        }
        return "images";
    }




    @PostMapping("/saveImage")
    public String save(@ModelAttribute("new_image") Image image, @RequestParam("pic") MultipartFile file) {

        imageService.save(image, file);
        return "redirect:/admin";
    }




    @GetMapping(value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {

        return imageService.getImage(imageName);
    }


}
