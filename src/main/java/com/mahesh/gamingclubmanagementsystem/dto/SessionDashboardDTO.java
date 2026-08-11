package com.mahesh.gamingclubmanagementsystem.dto;

import java.util.List;

import com.mahesh.gamingclubmanagementsystem.entity.GameSession;

public class SessionDashboardDTO {

    private Long runningSessions;

    private Long paymentPendingSessions;

    private Long completedSessions;

    private Double todayCollection;

    private List<GameSession> sessions;

    public Long getRunningSessions() {
        return runningSessions;
    }

    public void setRunningSessions(Long runningSessions) {
        this.runningSessions = runningSessions;
    }

    public Long getPaymentPendingSessions() {
        return paymentPendingSessions;
    }

    public void setPaymentPendingSessions(Long paymentPendingSessions) {
        this.paymentPendingSessions = paymentPendingSessions;
    }

    public Long getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Long completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Double getTodayCollection() {
        return todayCollection;
    }

    public void setTodayCollection(Double todayCollection) {
        this.todayCollection = todayCollection;
    }

    public List<GameSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<GameSession> sessions) {
        this.sessions = sessions;
    }
}