package com.example.pictures_annotator.infrastructure.inmemory;

import com.example.pictures_annotator.domain.models.BoundingBox;
import com.example.pictures_annotator.domain.repositories.BoundingBoxRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryBoundingBoxRepository implements BoundingBoxRepository {

    private final Map<Integer, BoundingBox> storage = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public BoundingBox save(BoundingBox boundingBox) {
        if (boundingBox.getId() == null) {
            boundingBox.setId(idGenerator.getAndIncrement());
        }
        storage.put(boundingBox.getId(), boundingBox);
        return boundingBox;
    }

    @Override
    public void modify(BoundingBox boundingBox) {
        Integer id = boundingBox.getId();

        if (id == null) {
            throw new IllegalArgumentException("Nie można modyfikować bounding boxa bez id");
        }

        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Nie znaleziono bounding boxa o id=" + id);
        }

        storage.put(id, boundingBox);
    }

    @Override
    public boolean existsById(Integer id){
        return storage.get(id) != null;
    }

    @Override
    public List<BoundingBox> findByPictureId(Integer pictureId) {
        return storage.values().stream()
                .filter(box -> box.getPictureId().equals(pictureId))
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        BoundingBox removed = storage.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Nie znaleziono bounding boxa o id=" + id);
        }
    }
}
