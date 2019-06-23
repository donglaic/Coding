package com.java24hours.ws;

import java.net.*;
import javax.xml.namespace.*;
import javax.xml.ws.*;

class SquareRootClient2 {
    public static void main(String[] arguments) throws Exception {
        URL url = new URL("http://127.0.0.1:5335/service?wsdl");
        QName qname = new QName(
            "http://ws.java24hours.com/",
            "SquareRootServerImpl2Service"
        );
        Service service = Service.create(url, qname);
        SquareRootServer2 srs = service.getPort(SquareRootServer2.class);

        System.out.println(srs.getTime());
        System.out.println(srs.getSquareRoot(625D));
        System.out.println(srs.multiply(13D));
    }
}