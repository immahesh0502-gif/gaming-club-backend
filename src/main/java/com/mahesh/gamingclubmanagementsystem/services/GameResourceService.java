package com.mahesh.gamingclubmanagementsystem.services;

import com.mahesh.gamingclubmanagementsystem.entity.GameResource;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceStatus;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceType;
import com.mahesh.gamingclubmanagementsystem.repository.GameResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameResourceService {

    @Autowired
    private GameResourceRepository gameResourceRepository;

    // Save Resource
    public GameResource save(GameResource resource) {

        // Check duplicate resource name
        if (gameResourceRepository.existsByName(resource.getName())) {
            throw new RuntimeException(resource.getName() + " already exists.");
        }

        // Every new resource starts as AVAILABLE
        resource.setStatus(ResourceStatus.AVAILABLE);

        // Set pricing automatically based on resource type
        if (resource.getType() == ResourceType.TABLE) {

            resource.setDayRate(4.0);
            resource.setNightRate(5.0);

        } else if (resource.getType() == ResourceType.PS5) {

            resource.setDayRate(100.0);
            resource.setNightRate(100.0);

        } else if (resource.getType() == ResourceType.CARROM) {

            resource.setDayRate(50.0);
            resource.setNightRate(50.0);
        }

        return gameResourceRepository.save(resource);
    }

    // Get All Resources
    public List<GameResource> getAllResources() {
        return gameResourceRepository.findAll();
    }

    // Get Resource By ID
    public GameResource getById(Long id) {
        return gameResourceRepository.findById(id).orElse(null);
    }

    // Delete Resource
    public void delete(Long id) {
        gameResourceRepository.deleteById(id);
    }
    // Update Resource
    public GameResource update(Long id, GameResource updatedResource) {

        GameResource resource = gameResourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        resource.setName(updatedResource.getName());
        resource.setType(updatedResource.getType());
        resource.setStatus(updatedResource.getStatus());
        resource.setDayRate(updatedResource.getDayRate());
        resource.setNightRate(updatedResource.getNightRate());

        return gameResourceRepository.save(resource);
    }
}