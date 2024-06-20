package com.petscreening.rental_service.controller.dtos;


public record NewPet(String name, String breed, Double weight, Boolean vaccinated, Integer level) {
}
