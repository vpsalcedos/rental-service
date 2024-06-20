package com.petscreening.rental_service.controller.dtos;

import java.util.List;

public record NewPetOwner(String name, String lastName, List<Long> petIds) {
}
