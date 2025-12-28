package com.pictures_annotator.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BoundingBox {

    private Integer id;

    private @Min(0) int x; //współrzędna x lewego górnego rogu boksa

    private @Min(0) int y; //współrzędna y lewego górnego rogu boksa

    private @Min(1) int width;

    private @Min(1) int height;

    private @NotBlank String label;

    public BoundingBox(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    public BoundingBox(Integer id, int x, int y, int width, int height, String label) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getX() { return x; }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLabel() {
        return label;
    }
}

