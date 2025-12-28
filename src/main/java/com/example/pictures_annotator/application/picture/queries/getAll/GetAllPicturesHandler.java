package com.example.pictures_annotator.application.picture.queries.getAll;

import com.example.pictures_annotator.domain.models.Picture;
import com.example.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPicturesHandler {
    private final PictureRepository pictureRepository;

    public GetAllPicturesHandler(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> handle(GetAllPicturesQuery command) {
        return pictureRepository.findAll();    }
}