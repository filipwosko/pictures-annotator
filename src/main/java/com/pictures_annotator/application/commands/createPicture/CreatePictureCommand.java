package com.pictures_annotator.application.commands.createPicture;

public record CreatePictureCommand(
        Integer id,
        byte[] data,
        int width,
        int height
) {}