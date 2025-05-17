package com.example.customerservice.CustomerManagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByIdAndDeletedFlag(Long id, Character deletedFlag);

    List<Customer> findAllByDeletedFlag(Character deletedFlag);

    @Query(value = """
        SELECT * FROM customer c
        WHERE c.deleted_flag = 'N'
        AND (:name IS NULL OR TRIM(CONCAT(c.first_name, ' ', c.last_name, ' ', COALESCE(c.other_name, ''))) ILIKE :name)
        AND (:startDate IS NULL OR c.created_at >= :startDate)
        AND (:endDate IS NULL OR c.created_at <= :endDate)
    """, nativeQuery = true)
    Page<Customer> search(@Param("name") String name,
                          @Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate,
                          Pageable pageable);
}