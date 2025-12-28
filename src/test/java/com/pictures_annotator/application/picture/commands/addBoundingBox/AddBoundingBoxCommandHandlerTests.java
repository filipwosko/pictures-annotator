package com.pictures_annotator.application.picture.commands.addBoundingBox;

import com.pictures_annotator.application.picture.BoundingBoxValidator;
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

class AddBoundingBoxCommandHandlerTest {

    private PictureRepository pictureRepository;
    private BoundingBoxValidator validator;
    private AddBoundingBoxCommandHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        validator = mock(BoundingBoxValidator.class);
        handler = new AddBoundingBoxCommandHandler(pictureRepository, validator);
    }

    @Test
    void handle_ShouldAddBoundingBox_WhenPictureExists() {
        // given
        byte[] data = new byte[]{0, 1, 2};
        Picture picture = new Picture(data, 800, 600);
        Integer pictureId = 1;
        AddBoundingBoxCommand command = new AddBoundingBoxCommand(pictureId, 10, 20, 100, 50, "label");

        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(picture));

        // when
        handler.handle(command);

        // then
        ArgumentCaptor<Picture> pictureCaptor = ArgumentCaptor.forClass(Picture.class);
        verify(pictureRepository).save(pictureCaptor.capture());
        Picture savedPicture = pictureCaptor.getValue();

        assertEquals(1, savedPicture.getBoundingBoxes().size());
        BoundingBox addedBox = savedPicture.getBoundingBoxes().get(0);
        assertEquals(10, addedBox.getX());
        assertEquals(20, addedBox.getY());
        assertEquals(100, addedBox.getWidth());
        assertEquals(50, addedBox.getHeight());
        assertEquals("label", addedBox.getLabel());

        verify(validator).validate(any(BoundingBox.class), eq(picture));
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
