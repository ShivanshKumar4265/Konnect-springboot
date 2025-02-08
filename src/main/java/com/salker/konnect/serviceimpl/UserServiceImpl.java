package com.salker.konnect.serviceimpl;


import com.salker.konnect.POJO.ConnectionIdAuthCode;
import com.salker.konnect.constant.CafeConstant;
import com.salker.konnect.dao.ConnectionDao;
import com.salker.konnect.service.UserService;
import com.salker.konnect.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    ConnectionDao connectionDao;
    @Override
    public ResponseEntity<Map<String, Object>> getConnection(Map<String, String> requestMap) {
        if (!"I0G2Fj7WkL9zF4".equals(requestMap.get("api_key"))) {
            return createResponse("failure", "Invalid API Key", null, HttpStatus.OK);
        }

        String connection_id = generateRandomCode(10);
        System.out.println("5656 Connection ID: " + connection_id);
        ConnectionIdAuthCode connectionIdAuthCode = new ConnectionIdAuthCode();
        connectionIdAuthCode.setConnectionId(connection_id);
        String currentTime = String.valueOf(new Date());
        connectionIdAuthCode.setCreated_at(currentTime);
        connectionIdAuthCode.setUpdated_at(currentTime);

        ConnectionIdAuthCode savedConnection = connectionDao.save(connectionIdAuthCode);
        System.out.println("5656 Saved Connection: " + savedConnection.toString());
        if (savedConnection == null || !savedConnection.getConnectionId().equalsIgnoreCase(connection_id)) {
            return createResponse("failure", "Connection ID generation failed", null, HttpStatus.OK);
        }

        return createResponse("success", "Connection ID generated.", savedConnection, HttpStatus.OK);
    }


    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // Random number from 0 to 9
        }
        return code.toString();
    }


    private ResponseEntity<Map<String, Object>> createResponse(String status, String message, Object data, HttpStatus statusCode) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, statusCode);
    }
}