package com.pictures_annotator.domain;

import com.pictures_annotator.domain.exceptions.BoundingBoxNotFoundException;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PictureTest {

    private Picture picture;
    private BoundingBox box1;
    private BoundingBox box2;

    @BeforeEach
    void setUp() {
        // Given
        picture = new Picture(new byte[]{1, 2, 3}, 100, 100);
        box1 = new BoundingBox(10, 20, 30, 40, "Label1");
        box2 = new BoundingBox(15, 25, 35, 45, "Label2");
    }

    @Test
    void shouldAddBoundingBoxAndAssignId() {
        // When
        picture.addBoundingBox(box1);

        // Then
        assertEquals(1, picture.getBoundingBoxes().size());
        assertEquals(box1, picture.getBoundingBoxes().get(0));
        assertEquals(1, box1.getId());
    }

    @Test
    void shouldAssignSequentialIdsWhenAddingMultipleBoundingBoxes() {
        // When
        picture.addBoundingBox(box1);
        picture.addBoundingBox(box2);

        // Then
        assertEquals(2, picture.getBoundingBoxes().size());
        assertEquals(1, box1.getId());
        assertEquals(2, box2.getId());
    }

    @Test
    void shouldModifyExistingBoundingBox() {
        // Given
        picture.addBoundingBox(box1);
        BoundingBox updatedBox = new BoundingBox(1, 15, 25, 35, 45, "UpdatedLabel");

        // When
        picture.modifyBoundingBox(updatedBox);

        // Then
        BoundingBox result = picture.getBoundingBoxes().get(0);
        assertEquals(1, result.getId());
        assertEquals(15, result.getX());
        assertEquals(25, result.getY());
        assertEquals(35, result.getWidth());
        assertEquals(45, result.getHeight());
        assertEquals("UpdatedLabel", result.getLabel());
    }

    @Test
    void shouldThrowExceptionWhenModifyingNonexistentBoundingBox() {
        // Given
        BoundingBox updatedBox = new BoundingBox(999, 0, 0, 10, 10, "Nonexistent");

        // When / Then
        assertThrows(BoundingBoxNotFoundException.class, () -> picture.modifyBoundingBox(updatedBox));
    }

    @Test
    void shouldDeleteBoundingBoxById() {
        // Given
        picture.addBoundingBox(box1);

        // When
        picture.deleteBoundingBox(1);

        // Then
        assertTrue(picture.getBoundingBoxes().isEmpty());
    }

    @Test
    void deletingNonexistentBoundingBoxShouldDoNothing() {
        // Given
        picture.addBoundingBox(box1);

        // When
        picture.deleteBoundingBox(999);

        // Then
        assertEquals(1, picture.getBoundingBoxes().size());
        assertEquals(box1, picture.getBoundingBoxes().get(0));
    }
}
