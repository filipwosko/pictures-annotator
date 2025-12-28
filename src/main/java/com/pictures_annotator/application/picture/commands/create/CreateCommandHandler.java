package com.pictures_annotator.application.picture.commands.create;

import com.pictures_annotator.application.picture.PictureValidator;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommandHandler {

    private final PictureRepository pictureRepository;
    private final PictureValidator validator;

    public CreateCommandHandler(
            PictureRepository pictureRepository,
            PictureValidator validator
    ) {
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public void handle(CreateCommand command) {
        Picture picture = new Picture(
                command.data(),
                command.width(),
                command.height()
        );

        validator.validate(picture);
        pictureRepository.save(picture);
    }
}
