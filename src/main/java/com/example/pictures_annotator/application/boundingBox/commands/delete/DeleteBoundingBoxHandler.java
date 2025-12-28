package com.example.pictures_annotator.application.boundingBox.commands.delete;

import com.example.pictures_annotator.domain.exceptions.BoundingBoxNotFoundException;
import com.example.pictures_annotator.domain.repositories.BoundingBoxRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBoundingBoxHandler {
    private final BoundingBoxRepository boundingBoxRepository;

    public DeleteBoundingBoxHandler(BoundingBoxRepository boundingBoxRepository) {
        this.boundingBoxRepository = boundingBoxRepository;
    }

    public void handle(DeleteBoundingBoxCommand command) {
        if (!boundingBoxRepository.existsById(command.id())){
            throw new BoundingBoxNotFoundException(command.id());
        }

        boundingBoxRepository.deleteById(command.id());
    }
}
