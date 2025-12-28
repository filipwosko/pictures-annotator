package com.pictures_annotator.domain.models;

import com.pictures_annotator.domain.exceptions.BoundingBoxNotFoundException;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Picture {

    private Integer id;

    private @NotNull byte[] data;

    private int width;
    private int height;

    private List<BoundingBox> boundingBoxes = new ArrayList<>();

    public Picture(@NotNull byte[] data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public byte[] getData() { return data; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public List<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    public void addBoundingBox(BoundingBox boundingBox) {
        int newId = 1;

        if (!boundingBoxes.isEmpty()) {
            BoundingBox lastBox = boundingBoxes.get(boundingBoxes.size() - 1);
            newId = lastBox.getId() + 1;
        }

        boundingBox.setId(newId);
        boundingBoxes.add(boundingBox);
    }


    public void modifyBoundingBox(BoundingBox updatedBox) {
        boolean exists = boundingBoxes.stream()
                .anyMatch(box -> box.getId().equals(updatedBox.getId()));

        if (!exists) {
            throw new BoundingBoxNotFoundException(updatedBox.getId());
        }

        boundingBoxes.replaceAll(box -> box.getId().equals(updatedBox.getId()) ? updatedBox : box);
    }


    public void deleteBoundingBox(Integer boundingBoxId) {
        boundingBoxes.removeIf(box -> box.getId().equals(boundingBoxId));
    }
}
