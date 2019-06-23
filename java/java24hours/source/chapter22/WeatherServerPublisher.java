package com.java24hours.ws;

import java.io.IOException;
import javax.xml.ws.*;
import nu.xom.ParsingException;

public class WeatherServerPublisher {
    public static void main(String[] arguments) throws ParsingException, IOException {
        WeatherServerImpl srsi = new WeatherServerImpl();
        Endpoint.publish(
            "http://127.0.0.1:5335/service",
            srsi
        );
    }
}