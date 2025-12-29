package com.pictures_annotator.application.picture;

import com.pictures_annotator.application.commands.validators.BoundingBoxValidator;
import com.pictures_annotator.application.commands.addBoundingBox.AddBoundingBoxCommand;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BoundingBoxValidatorTest

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
        assertDoesNotThrow(() -> { validator.validate(boundingBox, picture);});
    }

    @Test
    void handle_ShouldThrowException_WhenPictureNotFound() {
        // given
        Integer pictureId = 999;
        AddBoundingBoxCommand command = new AddBoundingBoxCommand(pictureId, 10, 20, 100, 50, "label");
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(PictureNotFoundException.class, () -> handler.handle(command));
        verify(pictureRepository, never()).save(any());
        verify(validator, never()).validate(any(), any());
    }
}

