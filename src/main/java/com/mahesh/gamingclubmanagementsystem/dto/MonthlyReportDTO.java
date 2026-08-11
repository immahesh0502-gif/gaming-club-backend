package com.mahesh.gamingclubmanagementsystem.dto;

public class MonthlyReportDTO {

    private Double revenue;

    private Integer totalSessions;

    private Integer totalCustomers;

    private Double averageBill;

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
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

    public Double getAverageBill() {
        return averageBill;
    }

    public void setAverageBill(Double averageBill) {
        this.averageBill = averageBill;
    }

}