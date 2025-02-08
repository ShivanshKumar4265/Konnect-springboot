package com.salker.konnect.restimpl;


import com.salker.konnect.rest.UserRest;
import com.salker.konnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<Map<String, Object>> getConnection(Map<String, String> requestMap) {
        try {
            return userService.getConnection(requestMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

