package com.mahesh.gamingclubmanagementsystem.dto;

import com.mahesh.gamingclubmanagementsystem.enums.ResourceType;

public class ResourceRequestDTO {

    private ResourceType type;
    private Integer resourceNumber;

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Integer getResourceNumber() {
        return resourceNumber;
    }

    public void setResourceNumber(Integer resourceNumber) {
        this.resourceNumber = resourceNumber;
    }
}