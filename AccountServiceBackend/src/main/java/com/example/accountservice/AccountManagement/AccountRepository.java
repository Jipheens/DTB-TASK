package com.example.accountservice.AccountManagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndDeletedFlag(Long id, char deletedFlag);

    // Native SQL query to support ILIKE for PostgreSQL
    @Query(value = """
        SELECT * FROM account a 
        WHERE (:iban IS NULL OR a.iban ILIKE %:iban%) 
        AND (:bicSwift IS NULL OR a.bic_swift ILIKE %:bicSwift%) 
        AND (:cardAlias IS NULL OR EXISTS (
            SELECT 1 FROM card c WHERE c.account_id = a.id AND c.card_alias ILIKE %:cardAlias%
        ))
        AND a.deleted_flag = 'N'
    """, nativeQuery = true)
    Page<Account> searchAccounts(@Param("iban") String iban,
                                 @Param("bicSwift") String bicSwift,
                                 @Param("cardAlias") String cardAlias,
                                 Pageable pageable);

    // Native SQL query for a similar search with LIKE instead of ILIKE
    @Query(value = """
        SELECT * FROM account a 
        WHERE (:iban IS NULL OR a.iban LIKE %:iban%) 
        AND (:bicSwift IS NULL OR a.bic_swift LIKE %:bicSwift%) 
        AND a.deleted_flag = 'N'
    """, nativeQuery = true)
    Page<Account> search(@Param("iban") String iban,
                         @Param("bicSwift") String bicSwift,
                         Pageable pageable);
}
