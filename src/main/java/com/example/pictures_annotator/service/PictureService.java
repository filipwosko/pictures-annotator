package com.example.pictures_annotator.service;

import com.example.pictures_annotator.model.Picture;
import com.example.pictures_annotator.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
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
                .orElseThrow(() -> new RuntimeException("Picture not found: " + id));
    }
}
