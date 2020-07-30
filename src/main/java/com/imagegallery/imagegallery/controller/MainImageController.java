package com.imagegallery.imagegallery.controller;


import com.imagegallery.imagegallery.model.Category;
import com.imagegallery.imagegallery.model.MainImage;
import com.imagegallery.imagegallery.model.Role;
import com.imagegallery.imagegallery.model.User;
import com.imagegallery.imagegallery.security.CurrentUser;
import com.imagegallery.imagegallery.service.CategoryService;
import com.imagegallery.imagegallery.service.EmailService;
import com.imagegallery.imagegallery.service.MainImageService;
import com.imagegallery.imagegallery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainImageController {

    private final CategoryService categoryService;
    private final MainImageService mainImageService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    private int id = 1;

    @GetMapping("/")
    public String getOne(ModelMap modelMap, @RequestParam(name = "id", required = false) Integer newId) {

        if (newId != null && newId != 0) {
            mainImageService.deleteById(this.id);
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
    public String save(@ModelAttribute("mainImage") MainImage mainImage, @RequestParam("pic") MultipartFile file) {
        mainImageService.save(file, mainImage);

        int id = mainImage.getId();
        return "redirect:/?id=" + id;
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

    @GetMapping("/loginPage")
    public String loginPage(ModelMap modelMap, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            modelMap.addAttribute("error", true);
        }
        return "loginPage";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) throws IOException {
        Optional<User> byUsername = userService.findByUsername(user.getUsername());

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return "loginPage";
        }
        if (byUsername.isPresent()) {

            return "loginPage";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        userService.save(user);
        String link = "http://localhost:8080/activate?email=" + user.getUsername() + "&token=" + user.getToken();
        emailService.send(user.getUsername(),
                "Welcome", "Dear " + user.getName() + " You have successfully registered. Please activate your account by clicking on: " + link);
        return "redirect:/";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("email") String email, @RequestParam("token") String token) {
        Optional<User> byUsername = userService.findByUsername(email);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            if (user.getToken().equals(token)) {
                user.setActive(true);
                user.setToken("");
                userService.save(user);
                return "redirect:/?msg=User was activate, please login";
            }
        }
        return "redirect:/?msg=Something went wrong. Please try again";
    }
}
