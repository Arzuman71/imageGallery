package com.imagegallery.imagegallery.repository;


import com.imagegallery.imagegallery.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {



    List<Image> findAllByCategory_Id(int id);





}
