package com.example.pictures_annotator.repository;

import com.example.pictures_annotator.model.BoundingBox;

import java.util.List;

public interface BoundingBoxRepository {

    BoundingBox save(BoundingBox boundingBox);

    void modify(BoundingBox boundingBox);

    List<BoundingBox> findByPictureId(Integer pictureId);

    void deleteById(Integer id);
}
