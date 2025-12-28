package com.pictures_annotator.application.picture.commands.create;

public record CreateCommand(
        Integer id,
        byte[] data,
        int width,
        int height
) {}