package com.example.pictures_annotator.domain.repository;

import com.example.pictures_annotator.domain.model.Picture;

import java.util.List;
import java.util.Optional;

public interface PictureRepository {

    Picture save(Picture picture);

    Optional<Picture> findById(Integer id);

    List<Picture> findAll();
}
