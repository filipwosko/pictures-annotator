package com.pictures_annotator.application.commands.addBoundingBox;

import com.pictures_annotator.application.commands.validators.BoundingBoxValidator;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class AddBoundingBoxCommandHandler {
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public AddBoundingBoxCommandHandler(PictureRepository pictureRepository, BoundingBoxValidator validator) {
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public void handle(AddBoundingBoxCommand command) {
        Picture picture = pictureRepository.findById(command.pictureId())
                .orElseThrow(() -> new PictureNotFoundException(command.pictureId()));

        BoundingBox boundingBox = new BoundingBox(
                command.x(), command.y(), command.width(), command.height(), command.label());

        validator.validate(boundingBox, picture);

        picture.addBoundingBox(boundingBox);
        pictureRepository.save(picture);
    }
}