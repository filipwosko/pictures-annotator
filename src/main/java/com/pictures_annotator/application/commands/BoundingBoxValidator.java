package com.pictures_annotator.application.commands;

import com.pictures_annotator.domain.exceptions.BoundingBoxOutOfImageException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.springframework.stereotype.Component;

@Component
public class BoundingBoxValidator {

    public void validate(BoundingBox boundingBox, Picture picture) {

        int imageWidth = picture.getWidth();
        int imageHeight = picture.getHeight();

        if (boundingBox.getX() < 0 || boundingBox.getY() < 0) {
            throw new BoundingBoxOutOfImageException("Bounding box nie może zaczynać się poza obrazem");
        }

        if (boundingBox.getWidth() <= 0 || boundingBox.getHeight() <= 0) {
            throw new BoundingBoxOutOfImageException("Szerokość i wysokość bounding boxa muszą być > 0");
        }

        if (boundingBox.getX() + boundingBox.getWidth() > imageWidth ||
                boundingBox.getY() + boundingBox.getHeight() > imageHeight) {
            throw new BoundingBoxOutOfImageException("Bounding box wykracza poza granice obrazu");
        }
    }
}
