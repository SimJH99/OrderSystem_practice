package com.encore.ordering.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object result;

    static public ResponseEntity<Map<String, Object>> responseMessage(HttpStatus status, Object object) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("success data", object);
        return new ResponseEntity<>(body, status);
    }
}
