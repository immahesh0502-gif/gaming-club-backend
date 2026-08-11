package com.mahesh.gamingclubmanagementsystem.dto;

public class StartSessionRequest {

    private Long customerId;

    private Long resourceId;

    private Integer playerCount = 1;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

}