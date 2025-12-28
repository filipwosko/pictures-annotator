package com.pictures_annotator.application.picture.commands.create;

import com.pictures_annotator.application.picture.PictureValidator;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateCommandHandlerTest {

    private PictureRepository pictureRepository;
    private PictureValidator validator;
    private CreateCommandHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        validator = mock(PictureValidator.class);
        handler = new CreateCommandHandler(pictureRepository, validator);
    }

    @Test
    void handle_givenValidCommand_shouldCallValidator() {
        // Given
        byte[] data = new byte[]{10, 20, 30};
        int width = 1;
        int height = 10;
        CreateCommand command = new CreateCommand(0, data, width, height);

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
    void handle_givenValidCommand_shouldSavePicture() {
        // Given
        byte[] data = new byte[]{10, 20, 30};
        int width = 1;
        int height = 10;
        CreateCommand command = new CreateCommand(0, data, width, height);

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
    void handle_whenValidatorThrows_shouldNotSavePicture() {
        // Given
        byte[] data = new byte[]{1, 2};
        int width = 2;
        int height = 2;
        CreateCommand command = new CreateCommand(0, data, width, height);
        doThrow(new RuntimeException("Invalid picture")).when(validator).validate(any(Picture.class));

        // When / Then
        assertThrows(RuntimeException.class, () -> handler.handle(command));

        verify(validator).validate(any(Picture.class));
        verifyNoInteractions(pictureRepository);
    }

    @Test
    void handle_givenDifferentPictureData_shouldSaveCorrectData() {
        // Given
        byte[] data = new byte[]{100, 101, 102};
        int width = 10;
        int height = 20;
        CreateCommand command = new CreateCommand(0, data, width, height);

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
    void handle_givenCommand_shouldCallValidatorBeforeSave() {
        // Given
        byte[] data = new byte[]{5, 6, 7};
        int width = 3;
        int height = 3;
        CreateCommand command = new CreateCommand(0, data, width, height);

        // When
        handler.handle(command);

        // Then
        verify(validator).validate(any(Picture.class));
        verify(pictureRepository).save(any(Picture.class));
        verifyNoMoreInteractions(validator, pictureRepository);
    }
}
