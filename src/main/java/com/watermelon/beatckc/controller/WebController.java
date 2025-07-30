package com.watermelon.beatckc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("/web")
@RestController
public class WebController {
    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> hello(
            @RequestParam String name,
            @RequestParam Integer age
    ){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> deleteUser(
            @RequestParam String name,
            @RequestParam Integer age,
            @PathVariable Integer id
    ){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("age", age);
        map.put("delete", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{name}/{age}")
    public ResponseEntity<HashMap<String,Object>> deleteUserTest(
            @PathVariable String name,
            @PathVariable Integer age,
            @PathVariable Integer id
    ){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("age", age);
        map.put("delete", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PostMapping("/putNewOne")
    public ResponseEntity<HashMap<String,Object>> putNewOne(
            @RequestBody HashMap<String,Object> map
    ){
        System.out.println(
                map.get("value").toString()
        );
        log.info(map.get("value").toString());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
