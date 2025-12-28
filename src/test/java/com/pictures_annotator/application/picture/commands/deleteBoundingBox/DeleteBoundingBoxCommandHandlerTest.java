package com.pictures_annotator.application.picture.commands.deleteBoundingBox;

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

public class DeleteBoundingBoxCommandHandlerTest {

    private PictureRepository pictureRepository;
    private DeleteBoundingBoxCommandHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        handler = new DeleteBoundingBoxCommandHandler(pictureRepository);
    }

    @Test
    void handle_givenExistingPicture_shouldDeleteBoundingBoxAndSave() {
        // Given
        Picture picture = new Picture(new byte[]{1, 2, 3}, 100, 100);
        picture.setId(1);
        BoundingBox box = new BoundingBox(1, 10, 10, 50, 50, "Label1");
        picture.addBoundingBox(box);

        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(picture.getId(), 1);
        when(pictureRepository.findById(picture.getId())).thenReturn(Optional.of(picture));

        // When
        handler.handle(command);

        // Then
        assertTrue(picture.getBoundingBoxes().isEmpty());

        ArgumentCaptor<Picture> captor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(captor.capture());
        assertEquals(picture, captor.getValue());
    }

    @Test
    void handle_givenNonExistingPicture_shouldThrowPictureNotFoundException() {
        // Given
        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(999, 1);
        when(pictureRepository.findById(999)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(PictureNotFoundException.class, () -> handler.handle(command));

        verify(pictureRepository, never()).save(any());
    }
}
