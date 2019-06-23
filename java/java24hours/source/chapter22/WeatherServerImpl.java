package com.java24hours.ws;

import java.io.IOException;
import java.util.*;
import javax.jws.*;
import nu.xom.ParsingException;

@WebService(endpointInterface = "com.java24hours.ws.WeatherServer")

public class WeatherServerImpl implements WeatherServer {
    WeatherStation station;

    public WeatherServerImpl() throws ParsingException, IOException {
        this.station = new WeatherStation();
    }
    public int getHighTemp() {
        return station.highTemp[0];
    }

    public int getLowTemp() {
        return station.lowTemp[0];
    }

    public String getConditions() {
        return station.conditions[0];
    }
}