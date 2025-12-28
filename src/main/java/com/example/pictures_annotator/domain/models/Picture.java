package com.example.pictures_annotator.domain.models;

import jakarta.validation.constraints.NotNull;

public class Picture {

    private Integer id;

    private @NotNull byte[] data;

    private int width;
    private int height;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}
