package com.pictures_annotator.application.commands.modifyBoundingBox;

import com.pictures_annotator.application.commands.BoundingBoxValidator;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModifyBoundingBoxCommandHandlerTest {

    private PictureRepository pictureRepository;
    private BoundingBoxValidator validator;
    private ModifyBoundingBoxCommandHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        validator = mock(BoundingBoxValidator.class);
        handler = new ModifyBoundingBoxCommandHandler(pictureRepository, validator);
    }

    @Test
    void handle_givenExistingPicture_shouldValidateModifyAndSaveBoundingBox() {
        // Given
        Picture picture = new Picture(new byte[]{1, 2, 3}, 100, 100);
        picture.setId(1);
        BoundingBox box = new BoundingBox(1, 10, 10, 50, 50, "OldLabel");
        picture.addBoundingBox(box);

        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(
                picture.getId(), 1, 20, 25, 60, 70, "NewLabel"
        );
        when(pictureRepository.findById(picture.getId())).thenReturn(Optional.of(picture));

        // When
        handler.handle(command);

        // Then
        ArgumentCaptor<BoundingBox> validatorCaptor = ArgumentCaptor.forClass(BoundingBox.class);
        verify(validator).validate(validatorCaptor.capture(), eq(picture));
        BoundingBox validatedBox = validatorCaptor.getValue();
        assertEquals(1, validatedBox.getId());
        assertEquals(20, validatedBox.getX());
        assertEquals(25, validatedBox.getY());
        assertEquals(60, validatedBox.getWidth());
        assertEquals(70, validatedBox.getHeight());
        assertEquals("NewLabel", validatedBox.getLabel());

        BoundingBox modifiedBox = picture.getBoundingBoxes().get(0);
        assertEquals(validatedBox.getId(), modifiedBox.getId());
        assertEquals(validatedBox.getX(), modifiedBox.getX());
        assertEquals(validatedBox.getY(), modifiedBox.getY());
        assertEquals(validatedBox.getWidth(), modifiedBox.getWidth());
        assertEquals(validatedBox.getHeight(), modifiedBox.getHeight());
        assertEquals(validatedBox.getLabel(), modifiedBox.getLabel());

        ArgumentCaptor<Picture> pictureCaptor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(pictureCaptor.capture());
        assertEquals(picture, pictureCaptor.getValue());
    }

    @Test
    void handle_givenNonExistingPicture_shouldThrowPictureNotFoundException() {
        // Given
        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(
                999, 1, 0, 0, 10, 10, "Label"
        );
        when(pictureRepository.findById(999)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(PictureNotFoundException.class, () -> handler.handle(command));

        verifyNoInteractions(validator);
        verify(pictureRepository, never()).save(any());
    }

    @Test
    void handle_whenValidatorThrows_shouldNotSavePicture() {
        // Given
        Picture picture = new Picture(new byte[]{1, 2, 3}, 50, 50);
        picture.setId(1);
        BoundingBox box = new BoundingBox(1, 5, 5, 10, 10, "Label");
        picture.addBoundingBox(box);

        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(
                picture.getId(), 1, 5, 5, 10, 10, "Label"
        );
        when(pictureRepository.findById(picture.getId())).thenReturn(Optional.of(picture));
        doThrow(new RuntimeException("Invalid bounding box")).when(validator).validate(any(BoundingBox.class), eq(picture));

        // When / Then
        assertThrows(RuntimeException.class, () -> handler.handle(command));

        verify(pictureRepository, never()).save(any());
    }
}
