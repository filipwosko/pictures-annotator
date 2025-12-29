package com.pictures_annotator.application.commands.validators;

import com.pictures_annotator.domain.exceptions.PictureNotValidException;
import com.pictures_annotator.domain.models.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PictureValidatorTest {

    private PictureValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PictureValidator();
    }

    private static byte[] validPngByteData = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00};

    private static Stream<byte[]> provideValidByteData() {
        return Stream.of(
                validPngByteData,
                new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, 0x00}
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidByteData")
    void validate_ShouldNotThrowException_WhenValidData(byte[] data) {
        // Given
        Picture picture = new Picture(data, 800, 600);

        // When
        validator.validate(picture);

        // Then
        assertDoesNotThrow(() -> validator.validate(picture));
    }


    private static Stream<Picture> provideInvalidPictures() {
        return Stream.of(
                new Picture(null, 1, 1),
                new Picture(new byte[]{}, 1, 1),
                new Picture(validPngByteData, 0, 1),
                new Picture(validPngByteData, 1, 0),
                new Picture(new byte[]{1, 2, 3, 4, 5}, 1, 0),
                new Picture(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47}, 1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPictures")
    void validate_ShouldTrowException_WhenInvalidData(Picture picture) {
        //When && Then
        assertThrows(PictureNotValidException.class, () -> validator.validate(picture));
    }

}
