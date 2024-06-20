package com.petscreening.rental_service.repository;

import com.petscreening.rental_service.model.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
}
