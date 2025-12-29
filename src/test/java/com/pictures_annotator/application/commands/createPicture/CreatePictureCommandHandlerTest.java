package com.pictures_annotator.application.commands.createPicture;

import com.pictures_annotator.application.commands.validators.PictureValidator;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreatePictureCommandHandlerTest {

    private PictureRepository pictureRepository;
    private PictureValidator validator;
    private CreatePictureCommandHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        validator = mock(PictureValidator.class);
        handler = new CreatePictureCommandHandler(pictureRepository, validator);
    }

    @Test
    void handle_ShouldCallValidator() {
        // Given
        byte[] data = new byte[]{10, 20, 30};
        int width = 1;
        int height = 10;
        CreatePictureCommand command = new CreatePictureCommand(0, data, width, height);

        // When
        handler.handle(command);

        // Then
        ArgumentCaptor<Picture> validatorCaptor = ArgumentCaptor.forClass(Picture.class);
        verify(validator).validate(validatorCaptor.capture());
        Picture validatedPicture = validatorCaptor.getValue();
        assertArrayEquals(data, validatedPicture.getData());
        assertEquals(width, validatedPicture.getWidth());
        assertEquals(height, validatedPicture.getHeight());
    }

    @Test
    void handle_ShouldNotSavePicture_WhenValidatorNotThrowsException() {
        // Given
        byte[] data = new byte[]{10, 20, 30};
        int width = 1;
        int height = 10;
        CreatePictureCommand command = new CreatePictureCommand(0, data, width, height);

        // When
        handler.handle(command);

        // Then
        ArgumentCaptor<Picture> repositoryCaptor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(repositoryCaptor.capture());
        Picture savedPicture = repositoryCaptor.getValue();
        assertArrayEquals(data, savedPicture.getData());
        assertEquals(width, savedPicture.getWidth());
        assertEquals(height, savedPicture.getHeight());
    }

    @Test
    void handle_ShouldNotSavePicture_WhenValidatorThrowsException() {
        // Given
        byte[] data = new byte[]{1, 2};
        int width = 2;
        int height = 2;
        CreatePictureCommand command = new CreatePictureCommand(0, data, width, height);
        doThrow(new RuntimeException("Invalid picture")).when(validator).validate(any(Picture.class));

        // When / Then
        assertThrows(RuntimeException.class, () -> handler.handle(command));

        verify(validator).validate(any(Picture.class));
        verifyNoInteractions(pictureRepository);
    }
}
