package com.example.smartparkingsystemapi.wrapper;

public class ApiResponse<T> {
    private String name;
    private T data;

    public ApiResponse(String name,T data){
        this.data = data;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }
}
