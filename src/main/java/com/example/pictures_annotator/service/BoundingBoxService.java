package com.example.pictures_annotator.service;

import com.example.pictures_annotator.model.BoundingBox;
import com.example.pictures_annotator.model.Picture;
import com.example.pictures_annotator.repository.BoundingBoxRepository;
import com.example.pictures_annotator.repository.PictureRepository;
import com.example.pictures_annotator.validation.BoundingBoxValidator;
import org.springframework.stereotype.Service;

@Service
public class BoundingBoxService {

    private final BoundingBoxRepository boundingBoxRepository;
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public BoundingBoxService(BoundingBoxRepository boundingBoxRepository,
                              PictureRepository pictureRepository,
                              BoundingBoxValidator validator) {
        this.boundingBoxRepository = boundingBoxRepository;
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public BoundingBox createBoundingBox(BoundingBox boundingBox) {
        Picture picture = pictureRepository.findById(boundingBox.getPictureId())
                .orElseThrow(() -> new RuntimeException(
                        "Nie znaleziono obrazu o id=" + boundingBox.getPictureId()
                ));

        validator.validate(boundingBox, picture);
        return boundingBoxRepository.save(boundingBox);
    }

    public void modifyBoundingBox(BoundingBox boundingBox){
        Picture picture = pictureRepository.findById(boundingBox.getPictureId())
                .orElseThrow(() -> new RuntimeException(
                        "Nie znaleziono obrazu o id=" + boundingBox.getPictureId()
                ));

        validator.validate(boundingBox, picture);
        boundingBoxRepository.modify(boundingBox);
    }

    public void deleteBoundingBox(Integer boxId) {
        boundingBoxRepository.deleteById(boxId);
    }
}


