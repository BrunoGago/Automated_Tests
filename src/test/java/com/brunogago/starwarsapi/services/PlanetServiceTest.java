package com.brunogago.starwarsapi.services;

import com.brunogago.starwarsapi.models.Planet;
import com.brunogago.starwarsapi.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.brunogago.starwarsapi.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//This annotation enable mockito to perform a unit test and not using spring boot to perform a single test
public class PlanetServiceTest {

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

}
