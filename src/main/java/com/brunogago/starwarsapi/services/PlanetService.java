package com.brunogago.starwarsapi.services;


import com.brunogago.starwarsapi.models.Planet;
import com.brunogago.starwarsapi.repositories.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository){
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet){
        return planetRepository.save(planet);
    }

    public Optional<Planet> get(Long id){
        return planetRepository.findById(id);
    }

}
