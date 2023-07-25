package com.brunogago.starwarsapi.repositories;


import com.brunogago.starwarsapi.models.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
}
