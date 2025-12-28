package com.pictures_annotator.infrastructure.inmemory;

import com.pictures_annotator.domain.models.Picture;
import com.pictures_annotator.domain.repositories.PictureRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryPictureRepository implements PictureRepository {

    private final Map<Integer, Picture> storage = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Picture save(Picture picture) {
        if (picture.getId() == null) {
            picture.setId(idGenerator.getAndIncrement());
        }
        storage.put(picture.getId(), picture);
        return picture;
    }

    @Override
    public Optional<Picture> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Picture> findAll() {
        return new ArrayList<>(storage.values());
    }
}
