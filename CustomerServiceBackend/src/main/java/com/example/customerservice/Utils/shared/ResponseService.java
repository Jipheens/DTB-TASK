package com.example.customerservice.Utils.shared;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Service
public class ResponseService {
    private static EntityResponse response = new EntityResponse();
    public static EntityResponse CREATED(String message, Object entity) {
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage(message.toUpperCase());
        response.setEntity(entity);
        return response;
    }
    public static EntityResponse FOUND(String message, Object entity) {
        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage(message.toUpperCase()+" "+HttpStatus.FOUND.getReasonPhrase().toUpperCase());
        response.setEntity(entity);
        return response;
    }
    public static EntityResponse NOT_FOUND(String message) {
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(message.toUpperCase());
        response.setEntity(null);
        return response;
    }
    public static EntityResponse NOT_ACCEPTABLE(String message) {
        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        response.setMessage(message.toUpperCase());
        response.setEntity(null);
        return response;
    }
    public static EntityResponse OK(String message, Object entity) {
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message.toUpperCase());
        response.setEntity(entity);
        return response;
    }
    public static EntityResponse MODIFIED(String message, Object entity) {
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message.toUpperCase()+" MODIFIED SUCCESSFULLY!");
        response.setEntity(entity);
        return response;
    }
    public static EntityResponse EXCEPTION(String message) {
        response.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
        response.setMessage(message.toUpperCase());
        response.setEntity("");
        return response;
    }
}
