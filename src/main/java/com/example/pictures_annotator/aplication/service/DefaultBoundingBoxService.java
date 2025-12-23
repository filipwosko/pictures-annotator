package com.example.pictures_annotator.aplication.service;

import com.example.pictures_annotator.domain.exception.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import com.example.pictures_annotator.aplication.validation.BoundingBoxValidator;
import org.springframework.stereotype.Service;

@Service
public class DefaultBoundingBoxService implements BoundingBoxService{

    private final BoundingBoxRepository boundingBoxRepository;
    private final PictureRepository pictureRepository;
    private final BoundingBoxValidator validator;

    public DefaultBoundingBoxService(BoundingBoxRepository boundingBoxRepository,
                                     PictureRepository pictureRepository,
                                     BoundingBoxValidator validator) {
        this.boundingBoxRepository = boundingBoxRepository;
        this.pictureRepository = pictureRepository;
        this.validator = validator;
    }

    public BoundingBox createBoundingBox(BoundingBox boundingBox) {
        Picture picture = pictureRepository.findById(boundingBox.getPictureId())
                .orElseThrow(() -> new PictureNotFoundException(
                        boundingBox.getPictureId()
                ));

        validator.validate(boundingBox, picture);
        return boundingBoxRepository.save(boundingBox);
    }

    public void modifyBoundingBox(BoundingBox boundingBox){
        if (!boundingBoxRepository.existsById(boundingBox.getId())){
            throw new BoundingBoxNotFoundException(boundingBox.getId());
        }

        Picture picture = pictureRepository.findById(boundingBox.getPictureId())
                .orElseThrow(() -> new PictureNotFoundException(
                        boundingBox.getPictureId()
                ));

        validator.validate(boundingBox, picture);
        boundingBoxRepository.modify(boundingBox);
    }

    public void deleteBoundingBox(Integer boundingBoxId) {
        boundingBoxRepository.deleteById(boundingBoxId);
    }
}


