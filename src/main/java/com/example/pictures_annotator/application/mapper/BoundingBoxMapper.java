package com.example.pictures_annotator.application.mapper;

import com.example.pictures_annotator.application.boundingBox.commands.create.CreateBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxCommand;
import com.example.pictures_annotator.domain.model.BoundingBox;

public interface BoundingBoxMapper {
    public BoundingBox mapCreateBoundingBoxCommandToBoundingBox(CreateBoundingBoxCommand command);
    public BoundingBox mapModifyBoundingBoxCommandToBoundingBox(ModifyBoundingBoxCommand command);
}
