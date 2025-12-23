package com.example.pictures_annotator.aplication.service;

import com.example.pictures_annotator.domain.model.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> listPictures();

    Picture createPicture(Picture picture);

    Picture getPictureById(Integer id);
}
