package com.mahesh.gamingclubmanagementsystem.dto;

public class DashboardDTO {

    private Double totalRevenue;
    private Integer totalSessions;
    private Integer totalCustomers;
    private Long activeSessions;
    private Long availableResources;
    private Long occupiedResources;
    private Long totalResources;

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

    public Long getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Long activeSessions) {
        this.activeSessions = activeSessions;
    }

    public Long getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(Long totalResources) {
        this.totalResources = totalResources;
    }

    public Long getAvailableResources() {
        return availableResources;
    }

    public void setAvailableResources(Long availableResources) {
        this.availableResources = availableResources;
    }

    public Long getOccupiedResources() {
        return occupiedResources;
    }

    public void setOccupiedResources(Long occupiedResources) {
        this.occupiedResources = occupiedResources;
    }
}