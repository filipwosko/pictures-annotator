package com.example.pictures_annotator.application.mapper;

import com.example.pictures_annotator.application.boundingBox.commands.create.CreateBoundingBoxCommand;
import com.example.pictures_annotator.application.boundingBox.commands.modify.ModifyBoundingBoxCommand;
import com.example.pictures_annotator.domain.model.BoundingBox;
import org.springframework.stereotype.Component;

@Component
public class DefaultBoundingBoxMapper implements BoundingBoxMapper {
    public BoundingBox mapCreateBoundingBoxCommandToBoundingBox(CreateBoundingBoxCommand command) {
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setPictureId(command.pictureId());
        boundingBox.setX(command.x());
        boundingBox.setY(command.y());
        boundingBox.setWidth(command.width());
        boundingBox.setHeight(command.height());
        boundingBox.setLabel(command.label());
        return boundingBox;
    }

    public BoundingBox mapModifyBoundingBoxCommandToBoundingBox(ModifyBoundingBoxCommand command) {
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setId(command.id()) ;
        boundingBox.setPictureId(command.pictureId());
        boundingBox.setX(command.x());
        boundingBox.setY(command.y());
        boundingBox.setWidth(command.width());
        boundingBox.setHeight(command.height());
        boundingBox.setLabel(command.label());
        return boundingBox;
    }
}
