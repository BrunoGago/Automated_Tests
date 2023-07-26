package com.brunogago.starwarsapi.services;

import com.brunogago.starwarsapi.builders.QueryBuilder;
import com.brunogago.starwarsapi.models.Planet;
import com.brunogago.starwarsapi.repositories.PlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.brunogago.starwarsapi.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//This annotation enable mockito to perform a unit test and not using spring boot to perform a single test
public class PlanetServiceTest {

    private String name;

    @BeforeEach
    void setUp() throws Exception {
        name = "Brunolito";
    }

    @InjectMocks//instances the class and its dependencies
    private PlanetService planetService;

    @Mock//Unit test of one dependency. The MockBean Annotation it's used to add mocks to a Spring ApplicationContext
    private PlanetRepository planetRepository;

    //test name: operation_state_return
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        //AAA: ARRANGE, ACTION, ASSERT

        //ARRANGE: Preparation of the data to perform the tests
        //Mockito will perform a fake action in the mockBean under use to perform the test
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        //ACTION: Action to be tested
        //SUT: system under test
        Planet sut = planetService.create(PLANET);

        //ASSERT: To verify a test by a comparing the return true (if the condition was satisfied) or false
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_TrowsException(){

        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);//Normally, RuntimeException occurs when there is an exception related to the DB

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET_WITHID));

        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET_WITHID);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty(){

        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.get(5L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        when(planetRepository.findByName("name")).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.getByName("name");

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty(){
        when(planetRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.getByName(name);

        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){
        List<Planet> planets = new ArrayList<>(){{
            add(PLANET);
        }};
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

        assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
    }
}
