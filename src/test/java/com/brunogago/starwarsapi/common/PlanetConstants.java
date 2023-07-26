package com.brunogago.starwarsapi.common;

import com.brunogago.starwarsapi.models.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");

    public static final Planet PLANET_WITHID = new Planet(1L, "name", "climate", "terrain");

    public static final Planet PLANET_WITHID_INVALID = new Planet(5L, "name", "climate", "terrain");

}
