package com.pictures_annotator.application.picture.queries.getAll;

import com.pictures_annotator.application.picture.PictureMapper;
import com.pictures_annotator.application.picture.queries.dto.PictureDto;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllQueryHandler {

    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public GetAllQueryHandler(PictureRepository pictureRepository, PictureMapper pictureMapper) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
    }

    public List<PictureDto> handle(GetAllQuery command) {
        return pictureRepository.findAll()
                .stream()
                .map(pictureMapper::map)
                .collect(Collectors.toList());
    }
}
