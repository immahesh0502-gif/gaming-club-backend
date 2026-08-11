package com.mahesh.gamingclubmanagementsystem.services;

import com.mahesh.gamingclubmanagementsystem.dto.ReportDTO;
import com.mahesh.gamingclubmanagementsystem.entity.GameSession;
import com.mahesh.gamingclubmanagementsystem.enums.PaymentMethod;
import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;
import com.mahesh.gamingclubmanagementsystem.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mahesh.gamingclubmanagementsystem.dto.MonthlyReportDTO;
import java.time.LocalDate;
import com.mahesh.gamingclubmanagementsystem.repository.BusinessDayRepository;

@Service
public class ReportService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private BusinessDayService businessDayService;

    @Autowired
    private BusinessDayRepository businessDayRepository;

    public ReportDTO getReport() {

        ReportDTO report = new ReportDTO();

        BusinessDay currentBusinessDay =
                businessDayService.getCurrentBusinessDay();

        var sessions = gameSessionRepository.findAll();



        double todayRevenue = 0;
        double todayCashRevenue = 0;
        double todayUpiRevenue = 0;
        double todayCardRevenue = 0;

        long todaySessions = 0;
        long runningSessions = 0;

        for (GameSession session : sessions) {

            System.out.println("----------------------------");
            System.out.println("Session ID : " + session.getId());

            System.out.println("Business Day : " +
                    (session.getBusinessDay() == null
                            ? "NULL"
                            : session.getBusinessDay().getId()));

            System.out.println("Status : " + session.getStatus());

            System.out.println("Amount : " + session.getTotalAmount());

            if (session.getBusinessDay() == null) {
                continue;
            }

            if (!session.getBusinessDay().getId().equals(currentBusinessDay.getId())) {
                continue;
            }

            if ("COMPLETED".equals(session.getStatus())
                    || "PAYMENT_PENDING".equals(session.getStatus())) {

                todaySessions++;

                double amount = session.getTotalAmount() == null
                        ? 0
                        : session.getTotalAmount();

                todayRevenue += amount;

                if (session.getPaymentMethod() == PaymentMethod.CASH) {

                    todayCashRevenue += amount;

                } else if (session.getPaymentMethod() == PaymentMethod.UPI) {

                    todayUpiRevenue += amount;

                } else if (session.getPaymentMethod() == PaymentMethod.CARD) {

                    todayCardRevenue += amount;

                }

            }

            if ("RUNNING".equals(session.getStatus())) {

                runningSessions++;

            }

        }

        double averageBill =
                todaySessions == 0
                        ? 0
                        : todayRevenue / todaySessions;


        report.setRunningSessions(runningSessions);
        report.setAverageBill(averageBill);
        report.setTodayCashRevenue(todayCashRevenue);
        report.setTodayUpiRevenue(todayUpiRevenue);
        report.setTodayCardRevenue(todayCardRevenue);

        report.setTodayRevenue(todayRevenue);

        report.setTodaySessions((int) todaySessions);

        report.setTodayCustomers(
                currentBusinessDay.getTotalCustomers()
        );

        return report;

    }
    public MonthlyReportDTO getMonthlyReport(int year, int month) {

        MonthlyReportDTO report = new MonthlyReportDTO();

        var sessions = gameSessionRepository.findAll();

        double revenue = 0;
        int totalSessions = 0;
        int totalCustomers = 0;

        java.util.Set<Long> customerIds = new java.util.HashSet<>();

        for (GameSession session : sessions) {

            if (!"COMPLETED".equals(session.getStatus())) {
                continue;
            }

            if (session.getEndTime() == null) {
                continue;
            }

            if (session.getEndTime().getYear() != year) {
                continue;
            }

            if (session.getEndTime().getMonthValue() != month) {
                continue;
            }

            totalSessions++;

            revenue += session.getTotalAmount() == null
                    ? 0
                    : session.getTotalAmount();

            if (session.getCustomer() != null) {
                customerIds.add(session.getCustomer().getId());
            }

        }

        totalCustomers = customerIds.size();

        double averageBill =
                totalSessions == 0
                        ? 0
                        : revenue / totalSessions;

        report.setRevenue(revenue);
        report.setTotalSessions(totalSessions);
        report.setTotalCustomers(totalCustomers);
        report.setAverageBill(averageBill);

        return report;
    }

    public ReportDTO getDailyReport(LocalDate date) {

        System.out.println("Daily Report Requested for : " + date);

        ReportDTO report = new ReportDTO();

        BusinessDay businessDay = businessDayRepository
                .findByBusinessDate(date)
                .orElseThrow(() -> new RuntimeException("Business Day Not Found"));

        var sessions = gameSessionRepository.findAll();

        double revenue = 0;
        double cashRevenue = 0;
        double upiRevenue = 0;
        double cardRevenue = 0;

        int completedSessions = 0;
        Long runningSessions = 0L;

        for (GameSession session : sessions) {

            if (session.getBusinessDay() == null) {
                continue;
            }

            if (!session.getBusinessDay().getId().equals(businessDay.getId())) {
                continue;
            }

            if ("COMPLETED".equals(session.getStatus())) {

                completedSessions++;

                double amount =
                        session.getTotalAmount() == null
                                ? 0
                                : session.getTotalAmount();

                revenue += amount;

                if (session.getPaymentMethod() == PaymentMethod.CASH) {

                    cashRevenue += amount;

                } else if (session.getPaymentMethod() == PaymentMethod.UPI) {

                    upiRevenue += amount;

                } else if (session.getPaymentMethod() == PaymentMethod.CARD) {

                    cardRevenue += amount;

                }

            }

            if ("RUNNING".equals(session.getStatus())) {

                runningSessions++;

            }

        }

        double averageBill =
                completedSessions == 0
                        ? 0
                        : revenue / completedSessions;

        report.setTodayRevenue(revenue);
        report.setTodayCashRevenue(cashRevenue);
        report.setTodayUpiRevenue(upiRevenue);
        report.setTodayCardRevenue(cardRevenue);

        report.setTodaySessions(completedSessions);
        report.setRunningSessions(runningSessions);
        report.setTodayCustomers(businessDay.getTotalCustomers());
        report.setAverageBill(averageBill);

        return report;

    }

}