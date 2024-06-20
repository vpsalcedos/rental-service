package com.petscreening.rental_service.service;

import com.petscreening.rental_service.controller.dtos.CustomPetFilter;
import com.petscreening.rental_service.exception.ResourceNotFoundException;
import com.petscreening.rental_service.model.Pet;
import com.petscreening.rental_service.repository.PetRepository;
import com.petscreening.rental_service.repository.PetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PetService {

    public static final String BREED_NOT_ALLOWED = "Poodle";
    private static final double MAXIMUM_ALLOWED_WEIGHT = 25;
    private static final int MINIMUM_TRAINING_LEVEL = 3;

    private PetRepository petRepository;
    private PetSpecification petSpecification;

    public PetService(PetRepository petRepository, PetSpecification petSpecification) {
        this.petRepository = petRepository;
        this.petSpecification = petSpecification;
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByFilter(CustomPetFilter filter) {
        Specification<Pet> petCustomFilter = null;
        if (filter.weightUnder() != null) {
            var weightSpecification = petSpecification.hasWeightUnder(filter.weightUnder().doubleValue());
            petCustomFilter = petCustomFilter == null ? Specification.where(weightSpecification) : petCustomFilter.and(weightSpecification);
        }

        if (filter.vaccinated() != null) {
            var vaccinatedSpecification = petSpecification.isVaccinated(filter.vaccinated().booleanValue());
            petCustomFilter = petCustomFilter == null ? Specification.where(vaccinatedSpecification) : petCustomFilter.and(vaccinatedSpecification);
        }

        if (filter.notBreed() != null) {
            var breedSpecification = petSpecification.hasBredDifferent(filter.notBreed());
            petCustomFilter = petCustomFilter == null ? Specification.where(breedSpecification) : petCustomFilter.and(breedSpecification);
        }

        if (filter.levelEqualsOrGreater() != null) {
            var levelSpecification = petSpecification.hasLevelAboveOrEqual(filter.levelEqualsOrGreater().intValue());
            petCustomFilter = petCustomFilter == null ? Specification.where(levelSpecification) : petCustomFilter.and(levelSpecification);
        }

        return petRepository.findAll(petCustomFilter);
    }

    public Pet createNewPet(String name, String breed, Double weight, Boolean vaccinated, Integer level) {
        var pet = Pet.builder()
                .level(level)
                .name(name)
                .breed(breed)
                .vaccinated(vaccinated)
                .weight(weight)
                .build();
        return petRepository.save(pet);
    }

    public boolean checkPetEligibility(Long id) {
        var pet = getPet(id);
        var isEligible = true;

        isEligible = validatePetRestriction(pet);
        return isEligible;
    }

    public boolean validatePetRestriction(Pet pet) {
        var isEligible = true;

        isEligible = isEligible & validateVaccination(pet);
        isEligible = isEligible & validateBreed(pet);
        isEligible = isEligible & validateWeight(pet);
        isEligible = isEligible & validateTrainingLevel(pet);

        return isEligible;
    }

    public boolean validateVaccination(Pet pet) {
        return pet.isVaccinated();
    }

    public boolean validateBreed(Pet pet) {
        return !pet.getBreed().equalsIgnoreCase(BREED_NOT_ALLOWED);
    }

    public boolean validateWeight(Pet pet) {
        return pet.getWeight() <= MAXIMUM_ALLOWED_WEIGHT;
    }

    public boolean validateTrainingLevel(Pet pet) {
        return pet.getLevel() >= MINIMUM_TRAINING_LEVEL;
    }

    private Pet getPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found with id:" + id));
    }

    public List<Pet> getPetsFromIds(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
