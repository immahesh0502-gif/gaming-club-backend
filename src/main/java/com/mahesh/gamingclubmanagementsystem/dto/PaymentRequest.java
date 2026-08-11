package com.mahesh.gamingclubmanagementsystem.dto;

import com.mahesh.gamingclubmanagementsystem.enums.PaymentMethod;

public class PaymentRequest {

    private Long sessionId;

    private PaymentMethod paymentMethod;

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
}