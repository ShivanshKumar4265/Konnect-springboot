package com.salker.konnect.serviceimpl;

import com.salker.konnect.POJO.ConnectionIdAuthCode;
import com.salker.konnect.POJO.User;
import com.salker.konnect.dao.AuthDao;
import com.salker.konnect.dao.UserDao;
import com.salker.konnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AuthDao authDao;

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<Map<String, Object>> getConnection(Map<String, String> requestMap) {
        if (!"I0G2Fj7WkL9zF4".equals(requestMap.get("api_key"))) {
            return createResponse("failure", "Invalid API Key", null, HttpStatus.OK);
        }

        String connection_id = generateRandomCode(20);
        System.out.println("5656 Connection ID: " + connection_id);
        ConnectionIdAuthCode connectionIdAuthCode = new ConnectionIdAuthCode();
        connectionIdAuthCode.setConnectionId(connection_id);
        String currentTime = String.valueOf(new Date());
        connectionIdAuthCode.setCreated_at(currentTime);
        connectionIdAuthCode.setUpdated_at(currentTime);

        ConnectionIdAuthCode savedConnection = authDao.save(connectionIdAuthCode);
        System.out.println("5656 Saved Connection: " + savedConnection.toString());
        Map<String, Object> response = new LinkedHashMap<>();

        if (savedConnection == null || !savedConnection.getConnectionId().equalsIgnoreCase(connection_id)) {
            response.put("status", "success");
            response.put("message", "Connection id generation failed");
            response.put("connection_id", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("status", "success");
        response.put("message", "Connection id generated successfully");
        response.put("connection_id", savedConnection.getConnectionId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addUser(Map<String, String> requestMap) {
        // Implement addUser logic here if needed
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> logIn(Map<String, String> requestMap) {
        // Step 1: Validate Connection ID
        ConnectionIdAuthCode connectionIdAuthCode = authDao.findByConnectionId(requestMap.get("connection_id"));

        // Check if a record exists for the provided connection_id
        if (connectionIdAuthCode == null || !requestMap.get("connection_id").equalsIgnoreCase(connectionIdAuthCode.getConnectionId())) {
            return createResponse("failure", "Invalid connection id.", null, HttpStatus.OK);
        }

        // Step 2: Validate User Credentials
        User user = userDao.findByEmail(requestMap.get("email"));
        if (user == null || !user.getStatus().equalsIgnoreCase("active")) {
            return createResponse("failure", "Invalid credentials or user is inactive.", null, HttpStatus.OK);
        }

        // Step 3: Validate Password
        if (!user.getPassword().equals(requestMap.get("password"))) {
            return createResponse("failure", "Incorrect password.", null, HttpStatus.OK);
        }

        // Step 4: Check if a record exists for this combination (connection_id, user_id)
        if (connectionIdAuthCode != null && connectionIdAuthCode.getUser() != null && connectionIdAuthCode.getUser().getId().equals(user.getId())) {
            // If user and connection ID combination exists, update the auth code
            String authCode = generateRandomAuthCode(20); // Generate new random auth code

            connectionIdAuthCode.setAuthCode(authCode);
            String currentTime = String.valueOf(new Date());
            connectionIdAuthCode.setUpdated_at(currentTime);

            // Save the updated ConnectionIdAuthCode object
            connectionIdAuthCode = authDao.save(connectionIdAuthCode);

            // Prepare response
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "User login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole().getRole());
            response.put("mobile", user.getPhone());
            response.put("email", user.getEmail());
            response.put("auth_code", connectionIdAuthCode.getAuthCode());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // If no existing record, create a new record for the connection and user
            String authCode = generateRandomAuthCode(20); // Generate random auth code

            // Set user and connection details
            connectionIdAuthCode.setAuthCode(authCode);
            connectionIdAuthCode.setUser(user);
            String currentTime = String.valueOf(new Date());
            connectionIdAuthCode.setUpdated_at(currentTime);

            // Save the new ConnectionIdAuthCode object
            connectionIdAuthCode = authDao.save(connectionIdAuthCode);

            // Prepare response
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "User login successful.");
            response.put("username", user.getUsername());
            response.put("role", user.getRole().getRole());
            response.put("mobile", user.getPhone());
            response.put("email", user.getEmail());
            response.put("auth_code", connectionIdAuthCode.getAuthCode());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }




    // Method to generate random numeric code
    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // Random number from 0 to 9
        }
        return code.toString();
    }

    // Method to generate random alphanumeric code
    private String generateRandomAuthCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // The character set

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length()); // Generate a random index in the character set
            code.append(characters.charAt(randomIndex)); // Append the character at the random index
        }

        return code.toString();
    }

    // Helper method to create response
    private ResponseEntity<Map<String, Object>> createResponse(String status, String message, Object data, HttpStatus statusCode) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, statusCode);
    }
}
