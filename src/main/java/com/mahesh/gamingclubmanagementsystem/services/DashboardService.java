package com.mahesh.gamingclubmanagementsystem.services;


import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;
import com.mahesh.gamingclubmanagementsystem.dto.DashboardDTO;

import com.mahesh.gamingclubmanagementsystem.enums.ResourceStatus;
import com.mahesh.gamingclubmanagementsystem.repository.GameResourceRepository;
import com.mahesh.gamingclubmanagementsystem.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahesh.gamingclubmanagementsystem.repository.CustomerRepository;

@Service
public class DashboardService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private GameResourceRepository gameResourceRepository;



    @Autowired
    private BusinessDayService businessDayService;

    public DashboardDTO getDashboard() {

        DashboardDTO dashboard = new DashboardDTO();

        try {

            BusinessDay currentBusinessDay =
                    businessDayService.getCurrentBusinessDay();

            dashboard.setTotalRevenue(
                    currentBusinessDay.getTotalRevenue()
            );

            dashboard.setTotalSessions(
                    currentBusinessDay.getTotalSessions()
            );

            dashboard.setTotalCustomers(
                    currentBusinessDay.getTotalCustomers()
            );

        } catch (RuntimeException e) {

            dashboard.setTotalRevenue(0.0);
            dashboard.setTotalSessions(0);
            dashboard.setTotalCustomers(0);

        }

        dashboard.setActiveSessions(
                gameSessionRepository.countRunningSessions()
        );

        Long availableResources =
                gameResourceRepository.countByStatus(ResourceStatus.AVAILABLE);

        Long occupiedResources =
                gameResourceRepository.countByStatus(ResourceStatus.OCCUPIED);

        dashboard.setAvailableResources(
                availableResources
        );

        dashboard.setOccupiedResources(
                occupiedResources
        );

        dashboard.setTotalResources(
                availableResources + occupiedResources
        );

        return dashboard;
    }

}