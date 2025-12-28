package com.pictures_annotator.application.picture;

import com.pictures_annotator.domain.exceptions.PictureNotValidException;
import com.pictures_annotator.domain.models.Picture;
import org.springframework.stereotype.Component;

@Component
public class PictureValidator {

    private static final long MAX_SIZE_BYTES = 12 * 1024 * 1024;

    public void validate(Picture picture) {
        if (picture.getWidth() <= 0 || picture.getHeight() <= 0) {
            throw new PictureNotValidException("Szerokość i wysokość obrazka muszą być > 0");
        }

        byte[] data = picture.getData();
        if (data == null || data.length == 0) {
            throw new PictureNotValidException("Obrazek nie zawiera danych binarnych.");
        }

        if (data.length > MAX_SIZE_BYTES) {
            throw new PictureNotValidException("Plik jest za duży. Maksymalny dozwolony rozmiar to 12MB.");
        }

        validateFileSignature(data);
    }

    private void validateFileSignature(byte[] data) {

        boolean isValid = false;

        //png
        isValid = data.length > 4 && (data[0] & 0xFF) == 0x89 && (data[1] & 0xFF) == 0x50 &&
                    (data[2] & 0xFF) == 0x4E && (data[3] & 0xFF) == 0x47;

        //jpeg/jpg
        if(!isValid) {
            isValid = data.length > 3 && (data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xD8 && (data[2] & 0xFF) == 0xFF;
        }

        if (!isValid) {
            throw new PictureNotValidException("Plik jest w niedozowlonym formacie. Dozwolone formaty to png i jpeg");
        }
    }
}