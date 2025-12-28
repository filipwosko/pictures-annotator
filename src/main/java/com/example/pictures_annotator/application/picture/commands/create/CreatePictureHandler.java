package com.example.pictures_annotator.application.picture.commands.create;

import com.example.pictures_annotator.application.picture.PictureMapper;
import com.example.pictures_annotator.application.picture.PictureValidator;
import com.example.pictures_annotator.domain.models.Picture;
import com.example.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePictureHandler {
    private final PictureMapper pictureMapper;
    private final PictureRepository pictureRepository;
    private final PictureValidator validator;

    public CreatePictureHandler(PictureRepository pictureRepository, PictureMapper pictureMapper, PictureRepository pictureRepository1, PictureValidator validator) {
        this.pictureMapper = pictureMapper;
        this.pictureRepository = pictureRepository1;
        this.validator = validator;
    }

    public void handle(CreatePictureCommand command) {
        Picture picture = pictureMapper.map(command);
        validator.validate(picture);
        pictureRepository.save(picture);
    }
}