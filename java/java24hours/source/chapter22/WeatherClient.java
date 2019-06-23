package com.java24hours.ws;

import java.net.*;
import javax.xml.namespace.*;
import javax.xml.ws.*;

class WeatherClient {
    public static void main(String[] arguments) throws Exception {
        URL url = new URL("http://127.0.0.1:5335/service?wsdl");
        QName qname = new QName(
            "http://ws.java24hours.com/",
            "WeatherServerImplService"
        );
        Service service = Service.create(url, qname);
        WeatherServer srs = service.getPort(WeatherServer.class);

        System.out.println("High temperature: " + srs.getHighTemp());
        System.out.println("Low temperature: " + srs.getLowTemp());
        System.out.println("Conditions: " + srs.getConditions());
    }
}