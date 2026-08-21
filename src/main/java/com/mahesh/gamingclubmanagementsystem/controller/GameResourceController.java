package com.mahesh.gamingclubmanagementsystem.controller;

import com.mahesh.gamingclubmanagementsystem.entity.GameResource;
import com.mahesh.gamingclubmanagementsystem.services.GameResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:5173")
public class GameResourceController {

    @Autowired
    private GameResourceService gameResourceService;

    @PostMapping
    public GameResource save(@RequestBody GameResource resource) {
        return gameResourceService.save(resource);
    }

    @GetMapping
    public List<GameResource> getAllResources() {
        return gameResourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public GameResource getById(@PathVariable Long id) {
        return gameResourceService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        gameResourceService.delete(id);
        return "Resource Deleted Successfully";
    }
    @PutMapping("/{id}")
    public GameResource update(
            @PathVariable Long id,
            @RequestBody GameResource resource) {

        return gameResourceService.update(id, resource);
    }
}