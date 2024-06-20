package com.petscreening.rental_service.controller.dtos;

public record CustomPetFilter(Double weightUnder, Boolean vaccinated, String notBreed, Integer levelEqualsOrGreater) {

}
