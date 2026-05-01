package com.example.smartparkingsystemapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OTP {
    @Id
    private Long id;
    private Integer otp;
    private Long expierytime;
    private Integer attempt;

    public OTP(){

    }

    public OTP(Long id, Long expierytime, Integer attempt, Integer otp) {
        this.id = id;
        this.expierytime = expierytime;
        this.attempt = attempt;
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpierytime() {
        return expierytime;
    }

    public void setExpierytime(Long expierytime) {
        this.expierytime = expierytime;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
