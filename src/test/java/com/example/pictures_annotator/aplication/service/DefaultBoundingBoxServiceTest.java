package com.example.pictures_annotator.aplication.service;

import com.example.pictures_annotator.aplication.validation.BoundingBoxValidator;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultBoundingBoxServiceTest {
    @InjectMocks private DefaultBoundingBoxService sut;

    @Mock private BoundingBoxRepository boundingBoxRepository;
    @Mock private PictureRepository pictureRepository;
    @Mock private BoundingBoxValidator validator;

    private BoundingBox boundingBox;
    private Picture picture;
    private Integer pictureId;
    private Integer boundingBoxId;

    @BeforeEach
    void setUp() {
        pictureId = 321;
        boundingBoxId = 456;

        boundingBox = new BoundingBox();
        boundingBox.setId(boundingBoxId);
        boundingBox.setPictureId(pictureId);

        picture = new Picture();
        picture.setId(pictureId);
    }

    @Test
    void createBoundingBox_shouldCreateWithSuccess_whenValidData() {
        // Arrange
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(picture));
        when(boundingBoxRepository.save(boundingBox)).thenReturn(boundingBox);

        // Act
        BoundingBox result = sut.createBoundingBox(boundingBox);

        // Assert
        assertNotNull(result);
        verify(validator).validate(boundingBox, picture);
        verify(boundingBoxRepository).save(boundingBox);
    }

    @Test
    void createBoundingBox_shouldThrowPictureNotFound_whenPictureDoesntExist() {
        // Arrange
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());

        // Act & Assert
        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class,
                () -> sut.createBoundingBox(boundingBox));

        assertTrue(exception.getMessage().contains("Nie odnaleziono obrazu o id: " + pictureId));
        verifyNoInteractions(validator);
        verify(boundingBoxRepository, never()).save(any());
    }

    @Test
    void modifyBoundingBox_shouldModifyWithSuccess_whenValidData() {
        // Arrange
        when(boundingBoxRepository.existsById(boundingBoxId)).thenReturn(true);
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(picture));

        // Act
        sut.modifyBoundingBox(boundingBox);

        // Assert
        verify(validator).validate(boundingBox, picture);
        verify(boundingBoxRepository).modify(boundingBox);
    }

    @Test
    void modifyBoundingBox_shouldThrowBoxNotFound_whenPictureDoesntExist() {
        // Arrange
        when(boundingBoxRepository.existsById(boundingBoxId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> sut.modifyBoundingBox(boundingBox));
        verify(boundingBoxRepository, never()).modify(any());
    }

    @Test
    void deleteBoundingBox_shouldDeleteBoxWithSuccess() {
        // Arrange
        Integer idToDelete = boundingBoxId;

        // Act
        sut.deleteBoundingBox(idToDelete);

        // Assert
        verify(boundingBoxRepository).deleteById(idToDelete);
    }
}