package com.example.pictures_annotator.application.picture;

import com.example.pictures_annotator.application.picture.commands.create.CreatePictureCommand;
import com.example.pictures_annotator.application.picture.queries.getById.GetPictureByIdResponse;
import com.example.pictures_annotator.domain.models.BoundingBox;
import com.example.pictures_annotator.domain.models.Picture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PictureMapper {
    public Picture map(CreatePictureCommand command) {
        Picture picture = new Picture();
        picture.setData(command.data());
        picture.setHeight(command.height());
        picture.setWidth(command.width());

        return picture;
    }

    public GetPictureByIdResponse map(Picture picture, List<BoundingBox> boxes) {
        return new GetPictureByIdResponse(
                picture.getId(),
                picture.getData(),
                picture.getWidth(),
                picture.getHeight(),
                boxes);
    }
}
