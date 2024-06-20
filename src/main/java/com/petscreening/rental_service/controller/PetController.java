package com.petscreening.rental_service.controller;

import com.petscreening.rental_service.controller.dtos.CustomPetFilter;
import com.petscreening.rental_service.controller.dtos.NewPet;
import com.petscreening.rental_service.controller.dtos.NewPetOwner;
import com.petscreening.rental_service.exception.InvalidValuesException;
import com.petscreening.rental_service.model.Pet;
import com.petscreening.rental_service.model.PetOwner;
import com.petscreening.rental_service.service.PetOwnerService;
import com.petscreening.rental_service.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PetController {

    private PetService petService;
    private PetOwnerService petOwnerService;

    public PetController(PetService petService, PetOwnerService petOwnerService) {
        this.petService = petService;
        this.petOwnerService = petOwnerService;
    }

    @QueryMapping
    public List<Pet> pets() {
        return petService.getPets();
    }

    @QueryMapping
    public List<Pet> petsByFilter(@Argument CustomPetFilter filter) {
        return petService.getPetsByFilter(filter);
    }

    @MutationMapping
    public Pet newPet(@Argument NewPet newPet) {
        return petService.createNewPet(newPet.name(), newPet.breed(), newPet.weight(), newPet.vaccinated(), newPet.level());
    }

    @QueryMapping
    public Boolean checkPetEligibility(@Argument Long id) {
        return petService.checkPetEligibility(id);
    }

    @MutationMapping
    public PetOwner newPetOwner(@Argument NewPetOwner newPetOwner) {
        if (newPetOwner.petIds() == null || newPetOwner.petIds().isEmpty()) {
            throw new InvalidValuesException("The pet owner must have at least one pet assigned");
        }
        return petOwnerService.createPetOwner(newPetOwner);
    }
}
