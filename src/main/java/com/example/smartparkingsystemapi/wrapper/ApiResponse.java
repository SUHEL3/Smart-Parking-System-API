package com.example.smartparkingsystemapi.wrapper;

public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(String message,T data){
        this.data = data;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
