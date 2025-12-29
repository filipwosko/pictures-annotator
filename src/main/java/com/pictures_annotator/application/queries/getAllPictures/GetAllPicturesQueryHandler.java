package com.pictures_annotator.application.queries.getAllPictures;

import com.pictures_annotator.application.queries.mappers.PictureMapper;
import com.pictures_annotator.application.queries.dto.PictureDto;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllPicturesQueryHandler {

    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public GetAllPicturesQueryHandler(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public List<PictureDto> handle(GetAllPicturesQuery command) {
        return pictureRepository.findAll()
                .stream()
                .map(pictureMapper::map)
                .collect(Collectors.toList());
    }
}
