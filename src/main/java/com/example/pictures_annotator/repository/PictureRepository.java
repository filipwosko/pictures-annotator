package com.example.pictures_annotator.repository;

import com.example.pictures_annotator.model.Picture;

import java.util.List;
import java.util.Optional;

public interface PictureRepository {

    Picture save(Picture picture);

    Optional<Picture> findById(Integer id);

    List<Picture> findAll();
}
