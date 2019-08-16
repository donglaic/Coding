package com.delay.apiAgent;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/testApi",produces = "application/json")
@CrossOrigin(origins = "*")
public class RestApiController {

    @GetMapping("/{num}")
    public ArrayList<Map> apiReturnStrings(
            @PathVariable("num") Long num
    ){
        ArrayList<Map> results = new ArrayList<>();
        Map<String,String> map;
        for (long i=0; i<num; i++){
            map = new HashMap<String,String>();
            map.put("url","http://loremflickr.com/150/150?random="+i);
            map.put("name","jason"+i);
            results.add(map);
        }
        return results;
    }

}
