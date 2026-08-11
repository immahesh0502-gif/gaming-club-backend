package com.mahesh.gamingclubmanagementsystem.repository;

import com.mahesh.gamingclubmanagementsystem.entity.GameResource;
import com.mahesh.gamingclubmanagementsystem.enums.ResourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResourceRepository extends JpaRepository<GameResource, Long> {

    Long countByStatus(ResourceStatus status);

    boolean existsByName(String name);
}