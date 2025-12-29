package com.pictures_annotator.application.queries.dto;

public record BoundingBoxDto(
    Integer id,
    int x,
    int y,
    int width,
    int height,
    String label
    ) {}
