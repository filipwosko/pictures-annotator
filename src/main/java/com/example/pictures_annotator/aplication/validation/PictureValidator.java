package com.example.pictures_annotator.aplication.validation;

import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;

public interface PictureValidator {
    void validate(Picture picture);
}
