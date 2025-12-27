package com.example.pictures_annotator.application.validation;

import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;

public interface BoundingBoxValidator {
    void validate(BoundingBox boundingBox, Picture picture);
}
