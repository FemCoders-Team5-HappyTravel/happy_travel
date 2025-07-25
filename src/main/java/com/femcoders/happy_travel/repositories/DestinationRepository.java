package com.femcoders.happy_travel.repositories;

import com.femcoders.happy_travel.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findAllByUserId(Long userId);


}
