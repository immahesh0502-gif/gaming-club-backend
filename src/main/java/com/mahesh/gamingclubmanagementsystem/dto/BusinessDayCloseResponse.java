package com.mahesh.gamingclubmanagementsystem.dto;

import java.time.LocalDate;

public class BusinessDayCloseResponse {

    private String message;
    private LocalDate businessDate;
    private Double totalRevenue;
    private Integer totalSessions;
    private Integer totalCustomers;

    public BusinessDayCloseResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Integer totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Integer getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Integer totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

}