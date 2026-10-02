package com.mahesh.gamingclubmanagementsystem.services;

import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;
import com.mahesh.gamingclubmanagementsystem.enums.BusinessDayStatus;
import com.mahesh.gamingclubmanagementsystem.repository.BusinessDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahesh.gamingclubmanagementsystem.repository.GameSessionRepository;
import com.mahesh.gamingclubmanagementsystem.dto.BusinessDayCloseResponse;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BusinessDayService {

    @Autowired
    private BusinessDayRepository businessDayRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public BusinessDay getCurrentBusinessDay() {

        return businessDayRepository
                .findByStatus(BusinessDayStatus.OPEN)
                .orElse(null);
    }
    public void updateRevenue(BusinessDay businessDay, Double amount) {

        if (amount == null) {
            amount = 0.0;
        }

        businessDay.setTotalRevenue(
                businessDay.getTotalRevenue() + amount
        );

        businessDayRepository.save(businessDay);

    }
    public void updateSessionCount(BusinessDay businessDay) {

        businessDay.setTotalSessions(
                businessDay.getTotalSessions() + 1
        );

        businessDayRepository.save(businessDay);

    }

    public void updateUniqueCustomers(BusinessDay businessDay) {

        Long customerCount =
                gameSessionRepository.countUniqueCustomers(
                        businessDay.getId()
                );

        businessDay.setTotalCustomers(customerCount.intValue());

        businessDayRepository.save(businessDay);

    }

    public void refreshBusinessDayTotals(BusinessDay businessDay) {

        if (businessDay == null) {
            return;
        }

        Long businessDayId = businessDay.getId();

        double totalRevenue = 0.0;
        int totalSessions = 0;

        java.util.Set<Long> customerIds = new java.util.HashSet<>();

        for (com.mahesh.gamingclubmanagementsystem.entity.GameSession session
                : gameSessionRepository.findAll()) {

            if (session.getBusinessDay() == null) {
                continue;
            }

            if (!businessDayId.equals(session.getBusinessDay().getId())) {
                continue;
            }

            if (!"COMPLETED".equals(session.getStatus())) {
                continue;
            }

            totalSessions++;

            if (session.getTotalAmount() != null) {
                totalRevenue += session.getTotalAmount();
            }

            if (session.getCustomer() != null) {
                customerIds.add(session.getCustomer().getId());
            }
        }

        businessDay.setTotalRevenue(totalRevenue);
        businessDay.setTotalSessions(totalSessions);
        businessDay.setTotalCustomers(customerIds.size());

        businessDayRepository.save(businessDay);
    }



    public BusinessDayCloseResponse closeBusinessDay(){
        System.out.println("========== CLOSE BUSINESS DAY CALLED ==========");

        BusinessDay businessDay = businessDayRepository
                .findByStatus(BusinessDayStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("No Open Business Day"));
        Long runningSessions = gameSessionRepository.countRunningSessions();

        if (runningSessions > 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Cannot close Business Day while sessions are running."
            );
        }

    BusinessDayCloseResponse response = new BusinessDayCloseResponse();

response.setMessage("Business Day Closed Successfully");
response.setBusinessDate(businessDay.getBusinessDate());
response.setTotalRevenue(businessDay.getTotalRevenue());
response.setTotalSessions(businessDay.getTotalSessions());
response.setTotalCustomers(businessDay.getTotalCustomers());

        businessDay.setActualCloseTime(LocalDateTime.now());

        businessDay.setStatus(BusinessDayStatus.CLOSED);

        businessDayRepository.save(businessDay);



return response;
    }

    public BusinessDay openBusinessDay() {

        if (businessDayRepository.findByStatus(BusinessDayStatus.OPEN).isPresent()) {
            throw new RuntimeException("Business Day Already Open");
        }

        BusinessDay businessDay = new BusinessDay();

        businessDay.setBusinessDate(LocalDate.now());

        businessDay.setOpenTime(LocalDateTime.now());

        businessDay.setExpectedCloseTime(
                LocalDate.now()
                        .plusDays(1)
                        .atTime(4, 0)
        );

        businessDay.setStatus(BusinessDayStatus.OPEN);

        businessDay.setTotalRevenue(0.0);
        businessDay.setTotalCustomers(0);
        businessDay.setTotalSessions(0);

        return businessDayRepository.save(businessDay);
    }

}