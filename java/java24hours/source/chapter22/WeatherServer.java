package com.java24hours.ws;

import javax.jws.*;
import javax.jws.soap.*;
import javax.jws.soap.SOAPBinding.*;

@WebService

@SOAPBinding(style = Style.RPC)

public interface WeatherServer {
    // get the current high temperature
    @WebMethod int getHighTemp();

    // get the current low temperature
    @WebMethod int getLowTemp();

    // get the current conditions
    @WebMethod String getConditions();

}