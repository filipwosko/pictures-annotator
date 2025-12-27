package com.example.pictures_annotator.aplication.boundingBox.commands.create;

import com.example.pictures_annotator.aplication.mapper.BoundingBoxMapper;
import com.example.pictures_annotator.aplication.validation.BoundingBoxValidator;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBoundingBoxHandler {
    private final BoundingBoxMapper boundingBoxMapper;
    private final BoundingBoxRepository boundingBoxRepository;
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public CreateBoundingBoxHandler(BoundingBoxRepository boundingBoxRepository, PictureRepository pictureRepository, BoundingBoxValidator validator, BoundingBoxMapper boundingBoxMapper) {
        this.boundingBoxRepository = boundingBoxRepository;
        this.pictureRepository = pictureRepository;
        this.validator = validator;
        this.boundingBoxMapper = boundingBoxMapper;
    }

    public Integer handle(CreateBoundingBoxCommand command) {
        Picture picture = pictureRepository.findById(command.pictureId())
                .orElseThrow(() -> new PictureNotFoundException(command.pictureId()));

        BoundingBox boundingBox = boundingBoxMapper.mapCreateBoundingBoxCommandToBoundingBox(command);

        validator.validate(boundingBox, picture);
        return boundingBoxRepository.save(boundingBox).getId();
    }
}