package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Catalogue;
import com.mycompany.myapp.repository.CatalogueRepository;
import com.mycompany.myapp.service.CatalogueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Catalogue}.
 */
@Service
@Transactional
public class CatalogueServiceImpl implements CatalogueService {

    private final Logger log = LoggerFactory.getLogger(CatalogueServiceImpl.class);

    private final CatalogueRepository catalogueRepository;

    public CatalogueServiceImpl(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @Override
    public Catalogue save(Catalogue catalogue) {
        log.debug("Request to save Catalogue : {}", catalogue);
        return catalogueRepository.save(catalogue);
    }

    @Override
    public Optional<Catalogue> partialUpdate(Catalogue catalogue) {
        log.debug("Request to partially update Catalogue : {}", catalogue);

        return catalogueRepository
            .findById(catalogue.getId())
            .map(existingCatalogue -> {
                if (catalogue.getNameOfAuthor() != null) {
                    existingCatalogue.setNameOfAuthor(catalogue.getNameOfAuthor());
                }
                if (catalogue.getNbOfCopies() != null) {
                    existingCatalogue.setNbOfCopies(catalogue.getNbOfCopies());
                }

                return existingCatalogue;
            })
            .map(catalogueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Catalogue> findAll() {
        log.debug("Request to get all Catalogues");
        return catalogueRepository.findAllWithEagerRelationships();
    }

    public Page<Catalogue> findAllWithEagerRelationships(Pageable pageable) {
        return catalogueRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Catalogue> findOne(Long id) {
        log.debug("Request to get Catalogue : {}", id);
        return catalogueRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Catalogue : {}", id);
        catalogueRepository.deleteById(id);
    }
}
