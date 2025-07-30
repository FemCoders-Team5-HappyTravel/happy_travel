package com.femcoders.happy_travel.repositories;

import com.femcoders.happy_travel.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Query("SELECT d FROM Destination d " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' " +
            "   OR LOWER(d.country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(d.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Destination> findBySearchTerm(@Param("searchTerm") String searchTerm);

    List<Destination> findAllByUserId(Long userId);




}
