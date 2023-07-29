package com.brunogago.starwarsapi.controllers;

import com.brunogago.starwarsapi.models.Planet;
import com.brunogago.starwarsapi.services.PlanetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.brunogago.starwarsapi.common.PlanetConstants.PLANET;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PlanetController.class)//Allows HTTP requests for tests
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;//the object "PLANET" will be serialized to a String (as a JSON)

    @MockBean//This annotation is used to "mock" the bean in a web context
    private PlanetService planetService;

    //the symbol "$" refers to the root object from the Json, we can use $.___ to refer to a specific item
    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
        //In mockMvcTest, the AAA are inside the block and the "sut" is created using the MockBean and Mockito
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception{
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("","","");

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(emptyPlanet)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(invalidPlanet)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
