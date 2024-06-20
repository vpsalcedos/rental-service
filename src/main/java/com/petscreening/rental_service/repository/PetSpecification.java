package com.petscreening.rental_service.repository;

import com.petscreening.rental_service.model.Pet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PetSpecification {

    public Specification<Pet> hasWeightUnder(double weight) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("weight"), weight);
    }

    public Specification<Pet> isVaccinated(boolean vaccinated) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("vaccinated"), vaccinated);
    }

    public Specification<Pet> hasBredDifferent(String breed) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("breed"), breed);
    }

    public Specification<Pet> hasLevelAboveOrEqual(int level) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"), level);
    }
}
