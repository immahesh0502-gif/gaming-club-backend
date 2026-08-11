package com.mahesh.gamingclubmanagementsystem.controller;

import com.mahesh.gamingclubmanagementsystem.dto.DashboardDTO;
import com.mahesh.gamingclubmanagementsystem.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardDTO getDashboard() {
        return dashboardService.getDashboard();
    }

}