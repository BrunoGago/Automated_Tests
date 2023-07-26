package com.brunogago.starwarsapi.services;

import com.brunogago.starwarsapi.models.Planet;
import com.brunogago.starwarsapi.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.brunogago.starwarsapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@SpringBootTest(classes = PlanetService.class)//This annotation upload the beans from Spring, but just from the class specified
@ExtendWith(MockitoExtension.class)//This annotation enable mockito to perform a unit test and not using spring boot to perform a single test
public class PlanetServiceTest {

    //@Autowired//This annotation should be used just when SpringBootTest is used as well.
    @InjectMocks//instances the class and its dependencies
    private PlanetService planetService;

    //@MockBean//This is a dummy injection in order to perform the test, since there is a dependency on the service class
    @Mock//Unit test of one dependency. The MockBean Annotation it's used to add mocks to a Spring ApplicationContext
    private PlanetRepository planetRepository;

    //test name: operation_state_return
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        //AAA: ARRANGE, ACTION, ASSERT

        //ARRANGE: Preparation of the data to perform the tests
        //Mockito will perform a fake action in the mockBean under use to perform the test
        when(planetRepository.save(PLANET)).thenReturn(PLANET);//STUB: this is a fake "action" from the repository with a fake return. It'll allow the test below to perform

        //ACTION: Action to be tested
        //SUT: system under test
        Planet sut = planetService.create(PLANET);

        //ASSERT: To verify a test by a comparing the return true (if the condition was satisfied) or false
        assertThat(sut).isEqualTo(PLANET);
    }
}
