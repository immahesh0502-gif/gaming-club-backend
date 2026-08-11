package com.mahesh.gamingclubmanagementsystem.entity;

import com.mahesh.gamingclubmanagementsystem.enums.ResourceStatus;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceType;
import jakarta.persistence.*;

@Entity
@Table(name = "game_resource")
public class GameResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Enumerated(EnumType.STRING)
    private ResourceStatus status;

    private Double dayRate;

    private Double nightRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public ResourceStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceStatus status) {
        this.status = status;
    }

    public Double getDayRate() {
        return dayRate;
    }

    public void setDayRate(Double dayRate) {
        this.dayRate = dayRate;
    }

    public Double getNightRate() {
        return nightRate;
    }

    public void setNightRate(Double nightRate) {
        this.nightRate = nightRate;
    }
}