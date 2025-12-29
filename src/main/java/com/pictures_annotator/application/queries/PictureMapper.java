package com.pictures_annotator.application.queries;

import com.pictures_annotator.application.queries.dto.BoundingBoxDto;
import com.pictures_annotator.application.queries.dto.PictureDto;
import com.pictures_annotator.domain.models.BoundingBox;
import com.pictures_annotator.domain.models.Picture;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PictureMapper {

    public PictureDto map(Picture picture){
        List<BoundingBoxDto> boundingBoxDtos = picture.getBoundingBoxes()
                .stream()
                .map(this::mapBoundingBox)
                .collect(Collectors.toList());

        return new PictureDto(
                picture.getId(),
                picture.getData(),
                picture.getWidth(),
                picture.getHeight(),
                boundingBoxDtos
        );
    }

    private BoundingBoxDto mapBoundingBox(BoundingBox box) {
        return new BoundingBoxDto(
                box.getId(),
                box.getX(),
                box.getY(),
                box.getWidth(),
                box.getHeight(),
                box.getLabel()
        );
    }
}
