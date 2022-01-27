package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Catalogue;
import com.mycompany.myapp.repository.CatalogueRepository;
import com.mycompany.myapp.service.CatalogueService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CatalogueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CatalogueResourceIT {

    private static final String DEFAULT_NAME_OF_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_AUTHOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_OF_COPIES = 1;
    private static final Integer UPDATED_NB_OF_COPIES = 2;

    private static final String ENTITY_API_URL = "/api/catalogues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Mock
    private CatalogueRepository catalogueRepositoryMock;

    @Mock
    private CatalogueService catalogueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueMockMvc;

    private Catalogue catalogue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue().nameOfAuthor(DEFAULT_NAME_OF_AUTHOR).nbOfCopies(DEFAULT_NB_OF_COPIES);
        return catalogue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createUpdatedEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue().nameOfAuthor(UPDATED_NAME_OF_AUTHOR).nbOfCopies(UPDATED_NB_OF_COPIES);
        return catalogue;
    }

    @BeforeEach
    public void initTest() {
        catalogue = createEntity(em);
    }

    @Test
    @Transactional
    void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();
        // Create the Catalogue
        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNameOfAuthor()).isEqualTo(DEFAULT_NAME_OF_AUTHOR);
        assertThat(testCatalogue.getNbOfCopies()).isEqualTo(DEFAULT_NB_OF_COPIES);
    }

    @Test
    @Transactional
    void createCatalogueWithExistingId() throws Exception {
        // Create the Catalogue with an existing ID
        catalogue.setId(1L);

        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCatalogues() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList
        restCatalogueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfAuthor").value(hasItem(DEFAULT_NAME_OF_AUTHOR)))
            .andExpect(jsonPath("$.[*].nbOfCopies").value(hasItem(DEFAULT_NB_OF_COPIES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCataloguesWithEagerRelationshipsIsEnabled() throws Exception {
        when(catalogueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCatalogueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(catalogueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCataloguesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(catalogueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCatalogueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(catalogueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get the catalogue
        restCatalogueMockMvc
            .perform(get(ENTITY_API_URL_ID, catalogue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogue.getId().intValue()))
            .andExpect(jsonPath("$.nameOfAuthor").value(DEFAULT_NAME_OF_AUTHOR))
            .andExpect(jsonPath("$.nbOfCopies").value(DEFAULT_NB_OF_COPIES));
    }

    @Test
    @Transactional
    void getNonExistingCatalogue() throws Exception {
        // Get the catalogue
        restCatalogueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findById(catalogue.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogue are not directly saved in db
        em.detach(updatedCatalogue);
        updatedCatalogue.nameOfAuthor(UPDATED_NAME_OF_AUTHOR).nbOfCopies(UPDATED_NB_OF_COPIES);

        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCatalogue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCatalogue))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNameOfAuthor()).isEqualTo(UPDATED_NAME_OF_AUTHOR);
        assertThat(testCatalogue.getNbOfCopies()).isEqualTo(UPDATED_NB_OF_COPIES);
    }

    @Test
    @Transactional
    void putNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catalogue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catalogue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue.nameOfAuthor(UPDATED_NAME_OF_AUTHOR);

        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNameOfAuthor()).isEqualTo(UPDATED_NAME_OF_AUTHOR);
        assertThat(testCatalogue.getNbOfCopies()).isEqualTo(DEFAULT_NB_OF_COPIES);
    }

    @Test
    @Transactional
    void fullUpdateCatalogueWithPatch() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue using partial update
        Catalogue partialUpdatedCatalogue = new Catalogue();
        partialUpdatedCatalogue.setId(catalogue.getId());

        partialUpdatedCatalogue.nameOfAuthor(UPDATED_NAME_OF_AUTHOR).nbOfCopies(UPDATED_NB_OF_COPIES);

        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCatalogue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCatalogue))
            )
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getNameOfAuthor()).isEqualTo(UPDATED_NAME_OF_AUTHOR);
        assertThat(testCatalogue.getNbOfCopies()).isEqualTo(UPDATED_NB_OF_COPIES);
    }

    @Test
    @Transactional
    void patchNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, catalogue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catalogue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();
        catalogue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatalogueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(catalogue))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        int databaseSizeBeforeDelete = catalogueRepository.findAll().size();

        // Delete the catalogue
        restCatalogueMockMvc
            .perform(delete(ENTITY_API_URL_ID, catalogue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
