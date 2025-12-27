package com.example.pictures_annotator.application.service;

import com.example.pictures_annotator.domain.model.BoundingBox;

public interface BoundingBoxService {

    BoundingBox createBoundingBox(BoundingBox boundingBox);

    void modifyBoundingBox(BoundingBox boundingBox);

    void deleteBoundingBox(Integer boxId);
}
