package com.example.pictures_annotator.aplication.service;

import com.example.pictures_annotator.aplication.validation.BoundingBoxValidator;
import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.BoundingBox;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Mock
    private BoundingBoxRepository boundingBoxRepository;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private BoundingBoxValidator validator;

    @InjectMocks
    private DefaultBoundingBoxService service;

    private BoundingBox sampleBox;
    private Picture samplePicture;

    @BeforeEach
    void setUp() {
        sampleBox = new BoundingBox();
        sampleBox.setId(1);
        sampleBox.setPictureId(100);

        samplePicture = new Picture();
        samplePicture.setId(100);
    }

    @Test
    @DisplayName("Powinien poprawnie stworzyć BoundingBox")
    void createBoundingBox_Success() {
        // given
        when(pictureRepository.findById(100)).thenReturn(Optional.of(samplePicture));
        when(boundingBoxRepository.save(sampleBox)).thenReturn(sampleBox);

        // when
        BoundingBox result = service.createBoundingBox(sampleBox);

        // then
        assertNotNull(result);
        verify(validator).validate(sampleBox, samplePicture);
        verify(boundingBoxRepository).save(sampleBox);
    }

    @Test
    @DisplayName("Powinien rzucić wyjątek przy tworzeniu, gdy obraz nie istnieje")
    void createBoundingBox_PictureNotFound() {
        // given
        when(pictureRepository.findById(100)).thenReturn(Optional.empty());

        // when & then
        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class,
                () -> service.createBoundingBox(sampleBox));

        assertTrue(exception.getMessage().contains("Nie odnaleziono obrazu o id: " + 100));
        verifyNoInteractions(validator);
        verify(boundingBoxRepository, never()).save(any());
    }

    @Test
    @DisplayName("Powinien poprawnie zmodyfikować BoundingBox")
    void modifyBoundingBox_Success() {
        // given
        when(boundingBoxRepository.existsById(1)).thenReturn(true);
        when(pictureRepository.findById(100)).thenReturn(Optional.of(samplePicture));

        // when
        service.modifyBoundingBox(sampleBox);

        // then
        verify(validator).validate(sampleBox, samplePicture);
        verify(boundingBoxRepository).modify(sampleBox);
    }

    @Test
    @DisplayName("Powinien rzucić wyjątek przy modyfikacji, gdy boks nie istnieje")
    void modifyBoundingBox_BoxNotFound() {
        // given
        when(boundingBoxRepository.existsById(1)).thenReturn(false);

        // when & then
        assertThrows(RuntimeException.class, () -> service.modifyBoundingBox(sampleBox));
        verify(boundingBoxRepository, never()).modify(any());
    }

    @Test
    @DisplayName("Powinien poprawnie usunąć BoundingBox")
    void deleteBoundingBox_Success() {
        // given
        Integer idToDelete = 1;

        // when
        service.deleteBoundingBox(idToDelete);

        // then
        verify(boundingBoxRepository).deleteById(idToDelete);
    }
}
