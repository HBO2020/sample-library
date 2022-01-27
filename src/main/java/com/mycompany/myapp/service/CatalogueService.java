package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Catalogue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Catalogue}.
 */
public interface CatalogueService {
    /**
     * Save a catalogue.
     *
     * @param catalogue the entity to save.
     * @return the persisted entity.
     */
    Catalogue save(Catalogue catalogue);

    /**
     * Partially updates a catalogue.
     *
     * @param catalogue the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Catalogue> partialUpdate(Catalogue catalogue);

    /**
     * Get all the catalogues.
     *
     * @return the list of entities.
     */
    List<Catalogue> findAll();

    /**
     * Get all the catalogues with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Catalogue> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" catalogue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Catalogue> findOne(Long id);

    /**
     * Delete the "id" catalogue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
