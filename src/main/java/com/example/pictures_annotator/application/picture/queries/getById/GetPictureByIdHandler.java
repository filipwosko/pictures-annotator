package com.example.pictures_annotator.application.picture.queries.getById;

import com.example.pictures_annotator.application.picture.PictureMapper;
import com.example.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.example.pictures_annotator.domain.models.BoundingBox;
import com.example.pictures_annotator.domain.models.Picture;
import com.example.pictures_annotator.domain.repositories.BoundingBoxRepository;
import com.example.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPictureByIdHandler {
    private final PictureRepository pictureRepository;
    private final BoundingBoxRepository boundingBoxRepository;
    private final PictureMapper pictureMapper;

    public GetPictureByIdHandler(PictureRepository pictureRepository, BoundingBoxRepository boundingBoxRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.boundingBoxRepository = boundingBoxRepository;
        this.pictureMapper = pictureMapper;
    }

    public GetPictureByIdResponse handle(GetPictureByIdQuery command) {
        Integer id = command.id();

        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(id));

        List<BoundingBox> boxes = boundingBoxRepository.findByPictureId(id);

        return pictureMapper.map(picture, boxes);
    }
}