package com.example.pictures_annotator.application.service;

import com.example.pictures_annotator.domain.exception.PictureNotFoundException;
import com.example.pictures_annotator.domain.model.Picture;
import com.example.pictures_annotator.domain.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPictureService implements PictureService{

    private final PictureRepository pictureRepository;

    public DefaultPictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> listPictures() {
        return pictureRepository.findAll();
    }

    public Picture createPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    public Picture getPictureById(Integer id) {
        return pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(id));
    }
}
