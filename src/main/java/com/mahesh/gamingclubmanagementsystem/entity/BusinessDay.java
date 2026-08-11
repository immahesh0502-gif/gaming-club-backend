package com.mahesh.gamingclubmanagementsystem.entity;

import com.mahesh.gamingclubmanagementsystem.enums.BusinessDayStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate businessDate;

    private LocalDateTime openTime;

    private LocalDateTime expectedCloseTime;

    private LocalDateTime actualCloseTime;

    private Double totalRevenue = 0.0;

    private Integer totalSessions = 0;

    private Integer totalCustomers = 0;

    @Enumerated(EnumType.STRING)
    private BusinessDayStatus status;
}