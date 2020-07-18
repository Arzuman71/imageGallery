package com.imagegallery.imagegallery.controller;


import com.imagegallery.imagegallery.model.Category;
import com.imagegallery.imagegallery.model.MainImage;
import com.imagegallery.imagegallery.service.CategoryService;
import com.imagegallery.imagegallery.service.MainImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainImageController {

    private final CategoryService categoryService;
    private final MainImageService mainImageService;
    private int id = 1;

    @GetMapping("/")
    public String getOne(ModelMap modelMap, @RequestParam(name = "id", required = false) Integer newId) {

        if (newId != null && newId != 0) {
            this.id = newId;
            return "admin";
        }
        List<Category> categories = categoryService.findAll();
        modelMap.addAttribute("categories", categories);

        MainImage mainImage = mainImageService.getOne(this.id);
        modelMap.addAttribute("mainImage", mainImage);
        return "index";

    }

    @PostMapping("/saveMainImage")
    public String save(@ModelAttribute("mainImage") MainImage mainImage, @RequestParam("pic") MultipartFile file, ModelMap modelMap) {
        mainImageService.save(file, mainImage);

        int id = mainImage.getId();
        return "redirect:/?id=" + id;
    }

    @GetMapping("/forTheFuture")
    public String forTheFuture() {


        return "about";
    }


    @PostMapping("/saveCategory")
    public String save(@ModelAttribute("category") Category category, @RequestParam("image") MultipartFile file) {
        categoryService.save(category, file);
        return "redirect:/";

    }

    @GetMapping("/admin")
    public String admin(ModelMap modelMap) {
        List<Category> categories = categoryService.findAll();
        modelMap.addAttribute("categories", categories);
        return "admin";
    }
}
