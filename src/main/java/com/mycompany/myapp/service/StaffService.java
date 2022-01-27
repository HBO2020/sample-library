package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Staff;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Staff}.
 */
public interface StaffService {
    /**
     * Save a staff.
     *
     * @param staff the entity to save.
     * @return the persisted entity.
     */
    Staff save(Staff staff);

    /**
     * Partially updates a staff.
     *
     * @param staff the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Staff> partialUpdate(Staff staff);

    /**
     * Get all the staff.
     *
     * @return the list of entities.
     */
    List<Staff> findAll();

    /**
     * Get the "id" staff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Staff> findOne(Long id);

    /**
     * Delete the "id" staff.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
