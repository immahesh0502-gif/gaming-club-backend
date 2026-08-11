package com.mahesh.gamingclubmanagementsystem.dto;

public class ReportDTO {

    private Double todayRevenue;
    private Integer todaySessions;
    private Integer todayCustomers;


    private Long runningSessions;
    private Double averageBill;



    private Double todayCashRevenue;
    private Double todayUpiRevenue;
    private Double todayCardRevenue;

    public Double getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(Double todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public Integer getTodaySessions() {
        return todaySessions;
    }

    public void setTodaySessions(Integer todaySessions) {
        this.todaySessions = todaySessions;
    }

    public Integer getTodayCustomers() {
        return todayCustomers;
    }

    public void setTodayCustomers(Integer todayCustomers) {
        this.todayCustomers = todayCustomers;
    }



    public Long getRunningSessions() {
        return runningSessions;
    }

    public void setRunningSessions(Long runningSessions) {
        this.runningSessions = runningSessions;
    }

    public Double getAverageBill() {
        return averageBill;
    }

    public void setAverageBill(Double averageBill) {
        this.averageBill = averageBill;
    }

    public Double getTodayCashRevenue() {
        return todayCashRevenue;
    }

    public void setTodayCashRevenue(Double todayCashRevenue) {
        this.todayCashRevenue = todayCashRevenue;
    }

    public Double getTodayUpiRevenue() {
        return todayUpiRevenue;
    }

    public void setTodayUpiRevenue(Double todayUpiRevenue) {
        this.todayUpiRevenue = todayUpiRevenue;
    }

    public Double getTodayCardRevenue() {
        return todayCardRevenue;
    }

    public void setTodayCardRevenue(Double todayCardRevenue) {
        this.todayCardRevenue = todayCardRevenue;
    }

}