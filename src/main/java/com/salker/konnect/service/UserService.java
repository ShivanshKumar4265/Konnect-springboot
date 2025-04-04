package com.salker.konnect.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    public ResponseEntity<Map<String, Object>> getConnection(Map<String, String> requestMap);
    public ResponseEntity<Map<String, Object>> addUser(Map<String, String> requestMap);
    public ResponseEntity<Map<String, Object>> logIn(Map<String, String> requestMap);
    public ResponseEntity<Map<String, Object>> createRole(Map<String, String> requestMap);
}
