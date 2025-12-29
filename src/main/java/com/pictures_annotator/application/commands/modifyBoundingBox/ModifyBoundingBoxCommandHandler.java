package com.pictures_annotator.application.commands.modifyBoundingBox;

import com.pictures_annotator.application.commands.BoundingBoxValidator;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class ModifyBoundingBoxCommandHandler {
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public ModifyBoundingBoxCommandHandler(PictureRepository pictureRepository, BoundingBoxValidator validator) {
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public void handle(ModifyBoundingBoxCommand command) {
        Picture picture = pictureRepository.findById(command.pictureId())
                .orElseThrow(() -> new PictureNotFoundException(command.pictureId()));

        BoundingBox boundingBox = new BoundingBox(
                command.id(), command.x(), command.y(), command.width(), command.height(), command.label());

        validator.validate(boundingBox, picture);
        picture.modifyBoundingBox(boundingBox);
        pictureRepository.save(picture);
    }
}
