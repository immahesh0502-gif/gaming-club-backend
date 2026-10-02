package com.mahesh.gamingclubmanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.mahesh.gamingclubmanagementsystem.enums.PaymentMethod;
import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;

@Entity
@Table(name = "game_session")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private GameResource resource;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double totalAmount;

    private String status;

    private Boolean paymentDone = false;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double cashAmount = 0.0;

    private Double upiAmount = 0.0;

    private Integer playerCount = 1;

    public Double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Double getUpiAmount() {
        return upiAmount;
    }

    public void setUpiAmount(Double upiAmount) {
        this.upiAmount = upiAmount;
    }

    @ManyToOne
    @JoinColumn(name = "business_day_id")
    private BusinessDay businessDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public GameResource getResource() {
        return resource;
    }

    public void setResource(GameResource resource) {
        this.resource = resource;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(Boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public BusinessDay getBusinessDay() {
        return businessDay;
    }

    public void setBusinessDay(BusinessDay businessDay) {
        this.businessDay = businessDay;
    }

}