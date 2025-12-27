package com.example.pictures_annotator.application.boundingBox.commands.modify;

import com.example.pictures_annotator.application.boundingBox.BoundingBoxMapper;
import com.example.pictures_annotator.application.boundingBox.BoundingBoxValidator;
import com.example.pictures_annotator.domain.exception.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class ModifyBoundingBoxHandler {
    private final BoundingBoxMapper boundingBoxMapper;
    private final BoundingBoxRepository boundingBoxRepository;
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public ModifyBoundingBoxHandler(BoundingBoxRepository boundingBoxRepository, PictureRepository pictureRepository, BoundingBoxValidator validator, BoundingBoxMapper boundingBoxMapper) {
        this.boundingBoxRepository = boundingBoxRepository;
        this.pictureRepository = pictureRepository;
        this.validator = validator;
        this.boundingBoxMapper = boundingBoxMapper;
    }

    public void handle(ModifyBoundingBoxCommand command) {
        if (!boundingBoxRepository.existsById(command.id())){
            throw new BoundingBoxNotFoundException(command.id());
        }

        Picture picture = pictureRepository.findById(command.pictureId())
                .orElseThrow(() -> new PictureNotFoundException(command.pictureId()));

        BoundingBox boundingBox = boundingBoxMapper.map(command);

        validator.validate(boundingBox, picture);
        boundingBoxRepository.save(boundingBox).getId();
    }
}
