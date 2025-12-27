package com.example.pictures_annotator.application.picture.commands.create;

public record CreatePictureCommand(
        Integer id,
        byte[] data,
        int width,
        int height
) {}