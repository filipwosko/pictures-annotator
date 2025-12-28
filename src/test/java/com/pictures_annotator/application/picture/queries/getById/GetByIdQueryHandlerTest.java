package com.pictures_annotator.application.picture.queries.getById;

import com.pictures_annotator.application.picture.PictureMapper;
import com.pictures_annotator.application.picture.queries.dto.PictureDto;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetByIdQueryHandlerTest {

    private PictureRepository pictureRepository;
    private PictureMapper pictureMapper;
    private GetByIdQueryHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        pictureMapper = mock(PictureMapper.class);
        handler = new GetByIdQueryHandler(pictureRepository, pictureMapper);
    }

    @Test
    void handle_givenExistingPicture_shouldReturnPictureDto() {
        // Given
        Picture picture = new Picture(new byte[]{1, 2, 3}, 100, 100);
        picture.setId(1);
        PictureDto dto = new PictureDto(1, new byte[]{1, 2, 3}, 100, 100, List.of());

        when(pictureRepository.findById(1)).thenReturn(Optional.of(picture));
        when(pictureMapper.map(picture)).thenReturn(dto);

        // When
        PictureDto result = handler.handle(new GetByIdQuery(1));

        // Then
        assertEquals(dto.id(), result.id());
        assertArrayEquals(dto.data(), result.data());
        assertEquals(dto.width(), result.width());
        assertEquals(dto.height(), result.height());
        assertEquals(dto.boundingBoxes(), result.boundingBoxes());

        verify(pictureRepository).findById(1);
        verify(pictureMapper).map(picture);
    }

    @Test
    void handle_givenNonExistingPicture_shouldThrowPictureNotFoundException() {
        // Given
        when(pictureRepository.findById(999)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(PictureNotFoundException.class, () -> handler.handle(new GetByIdQuery(999)));

        verify(pictureRepository).findById(999);
        verifyNoInteractions(pictureMapper);
    }
}
