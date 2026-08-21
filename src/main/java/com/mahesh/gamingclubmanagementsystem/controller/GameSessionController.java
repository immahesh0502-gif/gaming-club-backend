package com.mahesh.gamingclubmanagementsystem.controller;

import com.mahesh.gamingclubmanagementsystem.dto.SessionDashboardDTO;
import com.mahesh.gamingclubmanagementsystem.entity.GameSession;
import com.mahesh.gamingclubmanagementsystem.services.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mahesh.gamingclubmanagementsystem.dto.StartSessionRequest;
import com.mahesh.gamingclubmanagementsystem.dto.EndSessionRequest;
import java.util.List;
import com.mahesh.gamingclubmanagementsystem.dto.PaymentRequest;

@RestController
@RequestMapping("/api/sessions")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    // Save Session
    @PostMapping
    public GameSession save(@RequestBody GameSession session) {
        return gameSessionService.save(session);
    }
    @PostMapping("/start")
    public GameSession startSession(
            @RequestBody StartSessionRequest request) {

        return gameSessionService.startSession(request);
    }
    @PostMapping("/end")
    public GameSession endSession(
            @RequestBody EndSessionRequest request) {

        return gameSessionService.endSession(request.getSessionId());
    }
    @PostMapping("/payment")
    public GameSession makePayment(@RequestBody PaymentRequest request) {
        return gameSessionService.makePayment(request);
    }

    @GetMapping("/dashboard")
    public SessionDashboardDTO getSessionDashboard() {
        return gameSessionService.getSessionDashboard();
    }
    // Get All Sessions
    @GetMapping
    public List<GameSession> getAllSessions() {
        return gameSessionService.getAllSessions();
    }

    // Get Session By ID
    @GetMapping("/{id}")
    public GameSession getById(@PathVariable Long id) {
        return gameSessionService.getById(id);
    }

    // Delete Session
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        gameSessionService.delete(id);
        return "Session Deleted Successfully";
    }
}