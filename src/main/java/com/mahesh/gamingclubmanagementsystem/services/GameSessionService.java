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
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import com.mahesh.gamingclubmanagementsystem.enums.PaymentMethod;

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

        if (minutes < 0) {
            throw new RuntimeException("Invalid session duration");
        }
        System.out.println("Minutes Played : " + minutes);
        GameResource resource = session.getResource();

        double amount = 0;

        if (resource.getType() == ResourceType.TABLE) {

            LocalDateTime currentTime = session.getStartTime();

            // ==========================================
            // 1. BASE TABLE CHARGE
            // ==========================================

            for (int i = 0; i < minutes; i++) {

                int hour = currentTime.getHour();

                // 11 AM to 10 PM = Day Rate
                if (hour >= 11 && hour < 22) {

                    amount += resource.getDayRate();

                }
                // 10 PM to 3 AM = Night Rate
                else {

                    amount += resource.getNightRate();
                }

                currentTime = currentTime.plusMinutes(1);
            }


            // ==========================================
            // 2. EXTRA PLAYER CHARGE
            // ==========================================

            int playerCount = session.getPlayerCount() == null
                    ? 1
                    : session.getPlayerCount();

            // First 4 players are included
            int extraPlayers = Math.max(
                    0,
                    playerCount - 4
            );


            if (extraPlayers > 0) {

                // ₹50 per extra player per hour
                double extraPlayerCharge =
                        extraPlayers
                                * 50.0
                                * (minutes / 60.0);

                amount += extraPlayerCharge;


                System.out.println(
                        "Player Count       : " + playerCount
                );

                System.out.println(
                        "Extra Players       : " + extraPlayers
                );

                System.out.println(
                        "Extra Player Charge : ₹" + extraPlayerCharge
                );
            }


            // Round final amount
            amount = Math.round(amount);
        }
        else if (
                resource.getType() == ResourceType.PS5 ||
                        resource.getType() == ResourceType.PS4
        ) {

            int playerCount = session.getPlayerCount() == null
                    ? 1
                    : session.getPlayerCount();

            // Maximum 4 players because there are only 4 controllers
            if (playerCount < 1 || playerCount > 4) {
                throw new RuntimeException(
                        "PS4/PS5 supports maximum 4 players"
                );
            }

            // Minimum billing = 30 minutes
            long billableMinutes = Math.max(minutes, 30);

            double hourlyRate;

            if (resource.getType() == ResourceType.PS5) {

                // PS5 pricing
                switch (playerCount) {
                    case 1:
                        hourlyRate = 100.0;
                        break;

                    case 2:
                        hourlyRate = 150.0;
                        break;

                    case 3:
                        hourlyRate = 200.0;
                        break;

                    case 4:
                        hourlyRate = 250.0;
                        break;

                    default:
                        throw new RuntimeException(
                                "Invalid PS5 player count"
                        );
                }

            } else {

                // PS4 pricing
                switch (playerCount) {
                    case 1:
                        hourlyRate = 70.0;
                        break;

                    case 2:
                        hourlyRate = 120.0;
                        break;

                    case 3:
                        hourlyRate = 160.0;
                        break;

                    case 4:
                        hourlyRate = 200.0;
                        break;

                    default:
                        throw new RuntimeException(
                                "Invalid PS4 player count"
                        );
                }
            }

            // Proportional billing
            amount = Math.round(
                    hourlyRate * (billableMinutes / 60.0)
            );

            System.out.println(
                    "Resource Type : " + resource.getType()
            );

            System.out.println(
                    "Players       : " + playerCount
            );

            System.out.println(
                    "Actual Minutes: " + minutes
            );

            System.out.println(
                    "Billable Minutes: " + billableMinutes
            );

            System.out.println(
                    "Hourly Rate   : ₹" + hourlyRate
            );

            System.out.println(
                    "Amount        : ₹" + amount
            );
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
        System.out.println("totalAmount = " + session.getTotalAmount());


        // Prevent duplicate payment
        if ("COMPLETED".equals(session.getStatus())) {
            throw new RuntimeException("Payment already completed");
        }


        if (request.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method is required");
        }


        double totalAmount = session.getTotalAmount() == null
                ? 0
                : session.getTotalAmount();


        double cashAmount = request.getCashAmount() == null
                ? 0
                : request.getCashAmount();


        double upiAmount = request.getUpiAmount() == null
                ? 0
                : request.getUpiAmount();


        /*
         * ============================
         * CASH PAYMENT
         * ============================
         */

        if (request.getPaymentMethod() == PaymentMethod.CASH) {

            cashAmount = totalAmount;
            upiAmount = 0;
        }


        /*
         * ============================
         * UPI PAYMENT
         * ============================
         */

        else if (request.getPaymentMethod() == PaymentMethod.UPI) {

            cashAmount = 0;
            upiAmount = totalAmount;
        }


        /*
         * ============================
         * CARD PAYMENT
         * ============================
         */

        else if (request.getPaymentMethod() == PaymentMethod.CARD) {

            cashAmount = 0;
            upiAmount = 0;
        }


        /*
         * ============================
         * SPLIT PAYMENT
         * ============================
         */

        else if (request.getPaymentMethod() == PaymentMethod.SPLIT) {

            if (cashAmount < 0 || upiAmount < 0) {
                throw new RuntimeException(
                        "Payment amounts cannot be negative"
                );
            }


            double paidAmount = cashAmount + upiAmount;


            if (Math.abs(paidAmount - totalAmount) > 0.01) {

                throw new RuntimeException(
                        "Cash amount + UPI amount must equal total amount. "
                                + "Total: " + totalAmount
                                + ", Cash: " + cashAmount
                                + ", UPI: " + upiAmount
                );
            }
        }


        /*
         * ============================
         * SAVE PAYMENT DETAILS
         * ============================
         */

        session.setPaymentMethod(
                request.getPaymentMethod()
        );

        session.setCashAmount(cashAmount);

        session.setUpiAmount(upiAmount);

        session.setPaymentDone(true);

        session.setStatus("COMPLETED");


        /*
         * ============================
         * UPDATE BUSINESS DAY
         * ============================
         */

        businessDayService.refreshBusinessDayTotals(
                session.getBusinessDay()
        );


        System.out.println("===== AFTER SET =====");
        System.out.println(
                "paymentMethod = "
                        + session.getPaymentMethod()
        );

        System.out.println(
                "cashAmount = "
                        + session.getCashAmount()
        );

        System.out.println(
                "upiAmount = "
                        + session.getUpiAmount()
        );

        System.out.println(
                "paymentDone = "
                        + session.getPaymentDone()
        );

        System.out.println(
                "status = "
                        + session.getStatus()
        );


        gameSessionRepository.saveAndFlush(session);


        GameSession saved =
                gameSessionRepository
                        .findById(session.getId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Session Not Found After Save"
                                )
                        );


        System.out.println("===== AFTER SAVE =====");

        System.out.println(
                "paymentMethod = "
                        + saved.getPaymentMethod()
        );

        System.out.println(
                "cashAmount = "
                        + saved.getCashAmount()
        );

        System.out.println(
                "upiAmount = "
                        + saved.getUpiAmount()
        );

        System.out.println(
                "paymentDone = "
                        + saved.getPaymentDone()
        );

        System.out.println(
                "status = "
                        + saved.getStatus()
        );


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