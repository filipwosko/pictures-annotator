package com.example.pictures_annotator.application.picture.commands.create;

public record CreatePictureCommand(
        Integer id,
        byte[] data,
        String contentType,
        int width,
        int height
) {}