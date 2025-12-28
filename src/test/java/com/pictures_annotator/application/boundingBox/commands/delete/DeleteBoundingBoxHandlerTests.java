package com.pictures_annotator.application.boundingBox.commands.delete;

import com.pictures_annotator.application.picture.commands.deleteBoundingBox.DeleteBoundingBoxCommand;
import com.pictures_annotator.application.picture.commands.deleteBoundingBox.DeleteBoundingBoxCommandHandler;
import com.pictures_annotator.domain.exceptions.BoundingBoxNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBoundingBoxHandlerTests {

    @InjectMocks
    private DeleteBoundingBoxCommandHandler sut;

    @Mock
    private BoundingBoxRepository boundingBoxRepository;

    @Test
    void handle_shouldDeleteWithSuccess_whenBoxExists() {
        // Arrange
        Integer boxId = 555;
        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(boxId);

        when(boundingBoxRepository.existsById(boxId)).thenReturn(true);

        // Act
        sut.handle(command);

        // Assert
        verify(boundingBoxRepository).deleteById(boxId);
    }

    @Test
    void handle_shouldThrowBoundingBoxNotFound_whenBoxDoesNotExist() {
        // Arrange
        Integer boxId = 555;
        DeleteBoundingBoxCommand command = new DeleteBoundingBoxCommand(boxId);

        when(boundingBoxRepository.existsById(boxId)).thenReturn(false);

        // Act & Assert
        BoundingBoxNotFoundException exception = assertThrows(BoundingBoxNotFoundException.class,
                () -> sut.handle(command));

        assertTrue(exception.getMessage().contains(String.valueOf(boxId)));
        verify(boundingBoxRepository, never()).deleteById(any());
    }
}