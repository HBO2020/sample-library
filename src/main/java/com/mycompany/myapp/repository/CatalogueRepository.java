package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Catalogue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Catalogue entity.
 */
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {
    @Query(
        value = "select distinct catalogue from Catalogue catalogue left join fetch catalogue.books",
        countQuery = "select count(distinct catalogue) from Catalogue catalogue"
    )
    Page<Catalogue> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct catalogue from Catalogue catalogue left join fetch catalogue.books")
    List<Catalogue> findAllWithEagerRelationships();

    @Query("select catalogue from Catalogue catalogue left join fetch catalogue.books where catalogue.id =:id")
    Optional<Catalogue> findOneWithEagerRelationships(@Param("id") Long id);
}
