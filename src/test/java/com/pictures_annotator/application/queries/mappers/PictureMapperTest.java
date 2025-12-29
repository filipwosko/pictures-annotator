package com.pictures_annotator.application.queries.mappers;

import com.pictures_annotator.application.queries.dto.BoundingBoxDto;
import com.pictures_annotator.application.queries.dto.PictureDto;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PictureMapperTest {

    private PictureMapper pictureMapper;

    @BeforeEach
    void setUp() {
        pictureMapper = new PictureMapper();
    }

    @Test
    void map_ShouldMapCompletePictureToDto() {
        // Given
        byte[] imageData = new byte[]{1, 2, 3, 4};
        Picture picture = new Picture(imageData, 800, 600);
        picture.setId(1);

        picture.addBoundingBox(new BoundingBox(10, 10, 50, 50, "label1"));
        picture.addBoundingBox(new BoundingBox(70, 70, 20, 20, "label2"));

        // When
        PictureDto result = pictureMapper.map(picture);

        // Then
        assertNotNull(result);
        assertEquals(1, result.id());
        assertArrayEquals(imageData, result.data());
        assertEquals(800, result.width());
        assertEquals(600, result.height());

        List<BoundingBoxDto> boxes = result.boundingBoxes();
        assertEquals(2, boxes.size());

        BoundingBoxDto dto1 = boxes.get(0);
        assertEquals(1, dto1.id());
        assertEquals(10, dto1.x());
        assertEquals("label1", dto1.label());

        BoundingBoxDto dto2 = boxes.get(1);
        assertEquals(2, dto2.id());
        assertEquals(70, dto2.x());
        assertEquals("label2", dto2.label());
    }

    @Test
    void map_ShouldMapPictureWithNoBoxes() {
        // Given
        Picture picture = new Picture(new byte[]{0}, 100, 100);
        picture.setId(99);

        // When
        PictureDto result = pictureMapper.map(picture);

        // Then
        assertNotNull(result.boundingBoxes());
        assertTrue(result.boundingBoxes().isEmpty());
    }
}