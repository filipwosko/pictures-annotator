package com.pictures_annotator.application.picture.queries.getById;

import com.pictures_annotator.application.picture.PictureMapper;
import com.pictures_annotator.application.picture.queries.dto.PictureDto;
import com.pictures_annotator.domain.exceptions.PictureNotFoundException;
import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

@Service
public class GetByIdQueryHandler {
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public GetByIdQueryHandler(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public PictureDto handle(GetByIdQuery command) {
        Integer id = command.id();

        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(id));

        return pictureMapper.map(picture);
    }
}