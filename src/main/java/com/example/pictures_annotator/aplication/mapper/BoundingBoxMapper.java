package com.example.pictures_annotator.aplication.mapper;

import com.example.pictures_annotator.aplication.boundingBox.commands.create.CreateBoundingBoxCommand;
import com.example.pictures_annotator.aplication.boundingBox.commands.modify.ModifyBoundingBoxCommand;
import com.example.pictures_annotator.domain.model.BoundingBox;

public interface BoundingBoxMapper {
    public BoundingBox mapCreateBoundingBoxCommandToBoundingBox(CreateBoundingBoxCommand command);
    public BoundingBox mapModifyBoundingBoxCommandToBoundingBox(ModifyBoundingBoxCommand command);
}
