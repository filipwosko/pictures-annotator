package com.pictures_annotator.application.boundingBox.commands.create;

import com.pictures_annotator.application.boundingBox.BoundingBoxMapper;
import com.pictures_annotator.application.picture.BoundingBoxValidator;
import com.pictures_annotator.application.picture.commands.addBoundingBox.AddBoundingBoxCommand;
import com.pictures_annotator.application.picture.commands.addBoundingBox.AddBoundingBoxCommandHandler;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBoundingBoxHandlerTests {
    @InjectMocks private AddBoundingBoxCommandHandler sut;

    @Mock private BoundingBoxMapper boundingBoxMapper;
    @Mock private BoundingBoxRepository boundingBoxRepository;
    @Mock private PictureRepository pictureRepository;
    @Mock private BoundingBoxValidator validator;

    @Test
    void handle_shouldCreateWithSuccess_whenValidData() {
        // Arrange
        Integer pictureId = 1234;

        AddBoundingBoxCommand command = new AddBoundingBoxCommand(pictureId, 0, 0, 0, 0, "");

        Picture picture = new Picture();
        picture.setId(pictureId);

        BoundingBox boundingBox = new BoundingBox();

        when(boundingBoxMapper.map(command)).thenReturn(boundingBox);
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(picture));
        when(boundingBoxRepository.save(boundingBox)).thenReturn(boundingBox);

        // Act
        sut.handle(command);

        // Assert
        verify(validator).validate(boundingBox, picture);
        verify(boundingBoxRepository).save(boundingBox);
    }

    @Test
    void handle_shouldThrowPictureNotFound_whenPictureDoesntExist() {
        // Arrange
        Integer pictureId = 1234;
        AddBoundingBoxCommand command = new AddBoundingBoxCommand(pictureId, 0, 0, 0, 0, "");
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());

        // Act & Assert
        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class,
                () -> sut.handle(command));

        assertTrue(exception.getMessage().contains("Nie odnaleziono obrazu o id: " + pictureId));
        verifyNoInteractions(validator);
        verify(boundingBoxRepository, never()).save(any());
    }
}