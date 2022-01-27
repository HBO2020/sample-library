package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Image;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Image}.
 */
public interface ImageService {
    /**
     * Save a image.
     *
     * @param image the entity to save.
     * @return the persisted entity.
     */
    Image save(Image image);

    /**
     * Partially updates a image.
     *
     * @param image the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Image> partialUpdate(Image image);

    /**
     * Get all the images.
     *
     * @return the list of entities.
     */
    List<Image> findAll();

    /**
     * Get the "id" image.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Image> findOne(Long id);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
