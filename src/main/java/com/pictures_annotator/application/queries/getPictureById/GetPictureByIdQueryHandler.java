package com.pictures_annotator.application.queries.getPictureById;

import com.pictures_annotator.application.queries.mappers.PictureMapper;
import com.pictures_annotator.application.queries.dto.PictureDto;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPictureByIdQueryHandler {
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public GetPictureByIdQueryHandler(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public PictureDto handle(GetPictureByIdQuery command) {
        Integer id = command.id();

        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(id));

        return pictureMapper.map(picture);
    }
}