package com.pictures_annotator.application.commands.validators;

import com.pictures_annotator.domain.exceptions.BoundingBoxOutOfImageException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoundingBoxValidatorTest {

    private BoundingBoxValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BoundingBoxValidator();
    }

    @Test
    void validate_ShouldNotThrowException_WhenValidData() {
        // Given
        byte[] data = new byte[]{0, 1, 2};
        Picture picture = new Picture(data, 800, 600);
        BoundingBox boundingBox = new BoundingBox(1, 1, 1, 1, "");

        // When
        validator.validate(boundingBox, picture);

        // Then
        assertDoesNotThrow(() -> validator.validate(boundingBox, picture));
    }

    @ParameterizedTest(name = "[{index}] x={0}, y={1}, w={2}, h={3}")
    @CsvSource({
            "-1,  1, 1, 1",
            "1, -1,  1, 1",
            "1,  1,  0, 1",
            "1,  1, 1, 0",
            "701,  1, 100, 1",
            "1,  501, 1, 100"
    })
    void handle_ShouldThrowException_WhenInvalidData(int x, int y, int w, int h) {
        // Given
        byte[] data = new byte[]{0, 1, 2};
        Picture picture = new Picture(data, 800, 600);
        BoundingBox boundingBox = new BoundingBox(x, y, w, h, "");

        // When & Then
        assertThrows(BoundingBoxOutOfImageException.class, () -> validator.validate(boundingBox, picture));
    }
}

