package com.pictures_annotator.application.picture.commands.create;

import com.pictures_annotator.application.picture.PictureMapper;
import com.pictures_annotator.application.picture.PictureValidator;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommandHandler {
    private final PictureMapper pictureMapper;
    private final PictureRepository pictureRepository;
    private final PictureValidator validator;

    public CreateCommandHandler(PictureRepository pictureRepository, PictureMapper pictureMapper, PictureRepository pictureRepository1, PictureValidator validator) {
        this.pictureMapper = pictureMapper;
        this.pictureRepository = pictureRepository1;
        this.validator = validator;
    }

    public void handle(CreateCommand command) {
        Picture picture = new Picture(command.data(), command.height(), command.width());
        validator.validate(picture);
        pictureRepository.save(picture);
    }

    private Picture map(CreateCommand command) {
        Picture picture = new Picture(command.data(), command.height(), command.width());

        return picture;
    }
}