package com.example.cardservice.CardManagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = """
        SELECT * FROM card c
        WHERE (:alias IS NULL OR c.card_alias ILIKE %:alias%)
        AND (:type IS NULL OR c.type = :type)
        AND (:pan IS NULL OR c.pan ILIKE %:pan%)
        AND c.deleted_flag = 'N'
    """, nativeQuery = true)
    Page<Card> search(@Param("alias") String alias,
                      @Param("type") String type,
                      @Param("pan") String pan,
                      Pageable pageable);

    // Explicit query to avoid named parameter mismatch
    @Query("SELECT c FROM Card c WHERE c.id = :id AND c.deletedFlag = :deletedFlag")
    Optional<Card> findByIdAndDeletedFlag(@Param("id") Long id, @Param("deletedFlag") char deletedFlag);

    boolean existsByAccountIdAndType(Long accountId, String type);

    long countByAccountId(Long accountId);
}
