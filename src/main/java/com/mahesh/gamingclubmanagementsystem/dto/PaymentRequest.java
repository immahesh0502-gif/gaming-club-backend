package com.mahesh.gamingclubmanagementsystem.dto;

import com.mahesh.gamingclubmanagementsystem.enums.PaymentMethod;

public class PaymentRequest {

    private Long sessionId;

    private PaymentMethod paymentMethod;

    private Double cashAmount;

    private Double upiAmount;


    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


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
}