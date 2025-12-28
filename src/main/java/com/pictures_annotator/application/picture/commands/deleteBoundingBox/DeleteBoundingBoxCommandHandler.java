package com.pictures_annotator.application.picture.commands.deleteBoundingBox;

import com.pictures_annotator.domain.exceptions.BoundingBoxNotFoundException;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBoundingBoxCommandHandler {
    private final PictureRepository pictureRepository;

    public DeleteBoundingBoxCommandHandler(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public void handle(DeleteBoundingBoxCommand command) {
        Picture picture = pictureRepository.findById(command.pictureId())
                .orElseThrow(() -> new PictureNotFoundException(command.pictureId()));

        picture.deleteBoundingBox(command.boundingBoxId());
        pictureRepository.save(picture);
    }
}
