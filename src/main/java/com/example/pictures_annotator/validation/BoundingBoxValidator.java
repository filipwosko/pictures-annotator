package com.example.pictures_annotator.validation;

import com.example.pictures_annotator.model.BoundingBox;
import com.example.pictures_annotator.model.Picture;
import org.springframework.stereotype.Component;

@Component
public class BoundingBoxValidator {

    public void validate(BoundingBox box, Picture picture) {

        int imageWidth = picture.getWidth();
        int imageHeight = picture.getHeight();

        if (box.getX() < 0 || box.getY() < 0) {
            throw new RuntimeException("Bounding box nie może zaczynać się poza obrazem");
        }

        if (box.getWidth() <= 0 || box.getHeight() <= 0) {
            throw new RuntimeException("Szerokość i wysokość bounding boxa muszą być > 0");
        }

        if (box.getX() + box.getWidth() > imageWidth ||
                box.getY() + box.getHeight() > imageHeight) {
            throw new RuntimeException("Bounding box wykracza poza granice obrazu");
        }
    }
}
