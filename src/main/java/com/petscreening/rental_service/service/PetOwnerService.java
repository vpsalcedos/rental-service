package com.petscreening.rental_service.service;

import com.petscreening.rental_service.controller.dtos.NewPetOwner;
import com.petscreening.rental_service.model.PetOwner;
import com.petscreening.rental_service.repository.PetOwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PetOwnerService {

    private PetOwnerRepository petOwnerRepository;
    private PetService petService;

    public PetOwnerService(PetOwnerRepository petOwnerRepository, PetService petService) {
        this.petOwnerRepository = petOwnerRepository;
        this.petService = petService;
    }

    public PetOwner createPetOwner(NewPetOwner newPetOwner) {
        log.info("Creating new pet owner");
        var pets = petService.getPetsFromIds(newPetOwner.petIds());
        var petOwner = PetOwner.builder()
                .name(newPetOwner.name())
                .lastName(newPetOwner.lastName())
                .pets(pets)
                .build();
        return petOwnerRepository.save(petOwner);

    }
}
