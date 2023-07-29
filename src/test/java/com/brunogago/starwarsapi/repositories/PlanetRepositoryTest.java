package com.brunogago.starwarsapi.repositories;

import com.brunogago.starwarsapi.models.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import static com.brunogago.starwarsapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest(classes = PlanetRepository.class)//This annotation allow SpringBootTest to just call PlanetRepository
@DataJpaTest//H2 will be used to run tests that requires DB. Since we are using this annotation, the above will not be necessary
public class PlanetRepositoryTest {

    @Autowired//Since we are using SpringBootTest (Integration Test), we can use Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;//Allow us to consult the DB without using the repository (which one we are testing it)

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        Planet planet = planetRepository.save(PLANET);//Everything will be created in H2

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        System.out.println(planet);//Shows the object created in H2 DB

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("","","");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException(){
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);//Allows JPA to not observe this data in the db and not "update" in line 53
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

}
