package com.salker.konnect.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/auth")
public interface UserRest {

    @PostMapping(path = "/getConnection")
    public ResponseEntity<Map<String, Object>> getConnection(@RequestBody(required = true) Map<String, String> requestMap);

}
