package com.imagegallery.imagegallery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "main_image")
@Entity
public class MainImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String picUrl;
    private String title;
    private String description;
}
