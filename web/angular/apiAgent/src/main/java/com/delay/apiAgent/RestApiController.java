package com.delay.apiAgent;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping(value = "/testApi",produces = "application/json")
@CrossOrigin(origins = "*")
public class RestApiController {

    @GetMapping
    public Iterator<String> apiReturnStrings(){
        ArrayList<String> results = new ArrayList<>();
        results.add("hello");
        results.add("world");
        return results.iterator();
    }

}
