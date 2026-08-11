package com.mahesh.gamingclubmanagementsystem.repository;

import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;
import com.mahesh.gamingclubmanagementsystem.enums.BusinessDayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

import java.util.Optional;

public interface BusinessDayRepository extends JpaRepository<BusinessDay, Long> {

    Optional<BusinessDay> findByStatus(BusinessDayStatus status);

    Optional<BusinessDay> findByBusinessDate(LocalDate businessDate);

}