package com.pictures_annotator.application.commands.createPicture;

import com.pictures_annotator.application.commands.PictureValidator;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePictureCommandHandler {

    private final PictureRepository pictureRepository;
    private final PictureValidator validator;

    public CreatePictureCommandHandler(
            PictureRepository pictureRepository,
            PictureValidator validator
    ) {
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public void handle(CreatePictureCommand command) {
        Picture picture = new Picture(
                command.data(),
                command.width(),
                command.height()
        );

        validator.validate(picture);
        pictureRepository.save(picture);
    }
}
