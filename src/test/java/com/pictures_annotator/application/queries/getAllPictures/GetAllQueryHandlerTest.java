package com.pictures_annotator.application.queries.getAllPictures;

import com.pictures_annotator.application.queries.mappers.PictureMapper;
import com.pictures_annotator.application.queries.dto.BoundingBoxDto;
import com.pictures_annotator.application.queries.dto.PictureDto;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllQueryHandlerTest {

    private PictureRepository pictureRepository;
    private PictureMapper pictureMapper;
    private GetAllPicturesQueryHandler handler;

    @BeforeEach
    void setUp() {
        pictureRepository = mock(PictureRepository.class);
        pictureMapper = mock(PictureMapper.class);
        handler = new GetAllPicturesQueryHandler(pictureRepository, pictureMapper);
    }

    @Test
    void handle_ShouldReturnListOfPictureDtos() {
        // Given
        Picture picture1 = new Picture(new byte[]{1, 2, 3}, 10, 10);
        picture1.setId(1);
        Picture picture2 = new Picture(new byte[]{4, 5, 6}, 20, 20);
        picture2.setId(2);

        BoundingBoxDto boxDto1 = new BoundingBoxDto(1, 0, 0, 5, 5, "A");
        BoundingBoxDto boxDto2 = new BoundingBoxDto(2, 1, 1, 10, 10, "B");

        PictureDto dto1 = new PictureDto(1, new byte[]{1, 2, 3}, 10, 10, List.of(boxDto1));
        PictureDto dto2 = new PictureDto(2, new byte[]{4, 5, 6}, 20, 20, List.of(boxDto2));

        when(pictureRepository.findAll()).thenReturn(List.of(picture1, picture2));
        when(pictureMapper.map(picture1)).thenReturn(dto1);
        when(pictureMapper.map(picture2)).thenReturn(dto2);

        // When
        List<PictureDto> result = handler.handle(new GetAllPicturesQuery());

        // Then
        assertEquals(2, result.size());

        assertEquals(dto1.id(), result.get(0).id());
        assertArrayEquals(dto1.data(), result.get(0).data());
        assertEquals(dto1.width(), result.get(0).width());
        assertEquals(dto1.height(), result.get(0).height());
        assertEquals(dto1.boundingBoxes(), result.get(0).boundingBoxes());

        assertEquals(dto2.id(), result.get(1).id());
        assertArrayEquals(dto2.data(), result.get(1).data());
        assertEquals(dto2.width(), result.get(1).width());
        assertEquals(dto2.height(), result.get(1).height());
        assertEquals(dto2.boundingBoxes(), result.get(1).boundingBoxes());

        verify(pictureRepository).findAll();
        verify(pictureMapper).map(picture1);
        verify(pictureMapper).map(picture2);
    }
}
