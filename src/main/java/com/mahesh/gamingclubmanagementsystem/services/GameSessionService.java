package com.mahesh.gamingclubmanagementsystem.services;

import com.mahesh.gamingclubmanagementsystem.entity.GameSession;
import com.mahesh.gamingclubmanagementsystem.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahesh.gamingclubmanagementsystem.dto.StartSessionRequest;
import com.mahesh.gamingclubmanagementsystem.entity.Customer;
import com.mahesh.gamingclubmanagementsystem.entity.GameResource;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceStatus;
import com.mahesh.gamingclubmanagementsystem.repository.CustomerRepository;
import com.mahesh.gamingclubmanagementsystem.repository.GameResourceRepository;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceType;
import com.mahesh.gamingclubmanagementsystem.dto.PaymentRequest;
import com.mahesh.gamingclubmanagementsystem.dto.SessionDashboardDTO;
import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameResourceRepository gameResourceRepository;
    @Autowired
    private BusinessDayService businessDayService;


    // Save Session
    public GameSession save(GameSession session) {
        return gameSessionRepository.save(session);
    }

    // Get All Sessions
    public List<GameSession> getAllSessions() {
        return gameSessionRepository.findAll();
    }

    // Get Session By ID
    public GameSession getById(Long id) {
        return gameSessionRepository.findById(id).orElse(null);
    }

    // Delete Session
    public void delete(Long id) {
        gameSessionRepository.deleteById(id);
    }

    public GameSession startSession(StartSessionRequest request) {

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        GameResource resource = gameResourceRepository
                .findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource Not Found"));

        if(resource.getStatus() == ResourceStatus.OCCUPIED){
            throw new RuntimeException("Resource Already Occupied");
        }

        GameSession session = new GameSession();

        session.setCustomer(customer);

        session.setResource(resource);

        session.setStartTime(LocalDateTime.now());

        session.setBusinessDay(
                businessDayService.getCurrentBusinessDay()
        );

        System.out.println("Players Received = " + request.getPlayerCount());

        session.setPlayerCount(request.getPlayerCount());

        System.out.println("Players Received = " + request.getPlayerCount());
        System.out.println("Players Saved = " + session.getPlayerCount());

        session.setStatus("RUNNING");

        resource.setStatus(ResourceStatus.OCCUPIED);

        gameResourceRepository.save(resource);

        return gameSessionRepository.save(session);
    }

    public GameSession endSession(Long sessionId) {

        GameSession session = gameSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found"));

        session.setEndTime(LocalDateTime.now());

        long minutes = java.time.Duration
                .between(session.getStartTime(), session.getEndTime())
                .toMinutes();

        if (minutes == 0) {
            minutes = 1;
        }
        System.out.println("Minutes Played : " + minutes);
        GameResource resource = session.getResource();

        double amount = 0;

        if (resource.getType() == ResourceType.TABLE) {

            int hour = session.getStartTime().getHour();

            if (hour >= 10 && hour < 22) {
                amount = minutes * resource.getDayRate();
            } else {
                amount = minutes * resource.getNightRate();
            }

        }
        else if (resource.getType() == ResourceType.PS5) {

            double perMinuteRate = resource.getDayRate() / 60.0;

            System.out.println("Minutes = " + minutes);
            System.out.println("Players = " + session.getPlayerCount());
            System.out.println("Per Minute Rate = " + perMinuteRate);

            amount = Math.round(
                    minutes * session.getPlayerCount() * perMinuteRate
            );

            System.out.println("Amount = " + amount);

        }
        else if (resource.getType() == ResourceType.CARROM) {

            double perMinuteRate = 50.0 / 60.0;

            amount = Math.round(
                    minutes * perMinuteRate
            );

        }
        session.setTotalAmount(amount);

        session.setStatus("PAYMENT_PENDING");

        resource.setStatus(ResourceStatus.AVAILABLE);

        gameResourceRepository.save(resource);

        return gameSessionRepository.save(session);
    }

    public GameSession makePayment(PaymentRequest request) {

        GameSession session = gameSessionRepository
                .findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session Not Found"));

        System.out.println("===== BEFORE =====");
        System.out.println("paymentDone = " + session.getPaymentDone());
        System.out.println("status = " + session.getStatus());

        session.setPaymentMethod(request.getPaymentMethod());
        session.setPaymentDone(true);
        session.setStatus("COMPLETED");

        businessDayService.updateRevenue(
                session.getBusinessDay(),
                session.getTotalAmount()
        );
        businessDayService.updateSessionCount(
                session.getBusinessDay()
        );
        businessDayService.updateUniqueCustomers(
                session.getBusinessDay()
        );

        System.out.println("===== AFTER SET =====");
        System.out.println("paymentDone = " + session.getPaymentDone());
        System.out.println("status = " + session.getStatus());

        gameSessionRepository.saveAndFlush(session);

        GameSession saved = gameSessionRepository.findById(session.getId()).get();

        System.out.println("===== AFTER SAVE =====");
        System.out.println("paymentDone = " + saved.getPaymentDone());
        System.out.println("status = " + saved.getStatus());

        return saved;
    }

    public SessionDashboardDTO getSessionDashboard() {

        SessionDashboardDTO dto = new SessionDashboardDTO();

        BusinessDay currentBusinessDay =
                businessDayService.getCurrentBusinessDay();

        dto.setRunningSessions(
                gameSessionRepository.countRunningSessions()
        );

        dto.setPaymentPendingSessions(
                gameSessionRepository.countPaymentPendingSessions()
        );

        dto.setCompletedSessions(
                gameSessionRepository.countCompletedSessions(
                        currentBusinessDay.getId()
                )
        );

        dto.setTodayCollection(
                currentBusinessDay.getTotalRevenue()
        );

        dto.setSessions(
                gameSessionRepository.getTodaySessions(
                        currentBusinessDay.getId()
                )
        );

        return dto;
    }
}