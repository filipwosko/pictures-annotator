package com.example.pictures_annotator.application.boundingBox.modify;

import com.example.pictures_annotator.application.boundingBox.BoundingBoxMapper;
import com.example.pictures_annotator.application.boundingBox.BoundingBoxValidator;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxHandler;
import com.example.pictures_annotator.domain.exception.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyBoundingBoxHandlerTests {

    @InjectMocks
    private ModifyBoundingBoxHandler sut;

    @Mock private BoundingBoxMapper boundingBoxMapper;
    @Mock private BoundingBoxRepository boundingBoxRepository;
    @Mock private PictureRepository pictureRepository;
    @Mock private BoundingBoxValidator validator;

    @Test
    void handle_shouldModifyWithSuccess_whenValidData() {
        // Arrange
        Integer boxId = 1;
        Integer pictureId = 100;
        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(boxId, pictureId, 10, 10, 50, 50, "label");

        Picture picture = new Picture();
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setId(boxId);

        when(boundingBoxRepository.existsById(boxId)).thenReturn(true);
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(picture));
        when(boundingBoxMapper.map(command)).thenReturn(boundingBox);
        when(boundingBoxRepository.save(boundingBox)).thenReturn(boundingBox);

        // Act
        sut.handle(command);

        // Assert
        verify(validator).validate(boundingBox, picture);
        verify(boundingBoxRepository).save(boundingBox);
    }

    @Test
    void handle_shouldThrowBoundingBoxNotFound_whenBoxDoesNotExist() {
        // Arrange
        Integer boxId = 1;
        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(boxId, 100, 0, 0, 0, 0, "");

        when(boundingBoxRepository.existsById(boxId)).thenReturn(false);

        // Act & Assert
        assertThrows(BoundingBoxNotFoundException.class, () -> sut.handle(command));
        verifyNoInteractions(pictureRepository, boundingBoxMapper, validator);
        verify(boundingBoxRepository, never()).save(any());
    }

    @Test
    void handle_shouldThrowPictureNotFound_whenPictureDoesNotExist() {
        // Arrange
        Integer boxId = 1;
        Integer pictureId = 100;
        ModifyBoundingBoxCommand command = new ModifyBoundingBoxCommand(boxId, pictureId, 0, 0, 0, 0, "");

        when(boundingBoxRepository.existsById(boxId)).thenReturn(true);
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PictureNotFoundException.class, () -> sut.handle(command));
        verifyNoInteractions(boundingBoxMapper, validator);
        verify(boundingBoxRepository, never()).save(any());
    }
}