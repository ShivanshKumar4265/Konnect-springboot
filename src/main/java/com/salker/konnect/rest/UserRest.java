package com.salker.konnect.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;

@RequestMapping(path = "/auth")
public interface UserRest {

    @PostMapping(path = "/getConnection")
    public ResponseEntity<Map<String, Object>> getConnection(@RequestBody(required = true) Map<String, String> requestMap);


    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/create_role")
    public ResponseEntity<Map<String, Object>> createRole(@RequestBody(required = true) Map<String, String> requestMap);

}
