package com.example.pictures_annotator.domain.repositories;

import com.example.pictures_annotator.domain.models.BoundingBox;

import java.util.List;

public interface BoundingBoxRepository {

    BoundingBox save(BoundingBox boundingBox);

    void modify(BoundingBox boundingBox);

    boolean existsById(Integer id);

    List<BoundingBox> findByPictureId(Integer pictureId);

    void deleteById(Integer id);
}
