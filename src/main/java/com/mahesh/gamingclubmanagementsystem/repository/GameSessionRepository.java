package com.mahesh.gamingclubmanagementsystem.repository;

import com.mahesh.gamingclubmanagementsystem.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    @Query("SELECT SUM(g.totalAmount) FROM GameSession g WHERE g.paymentDone = true")
    Double getTotalRevenue();

    @Query("SELECT COUNT(g) FROM GameSession g")
    Long getTotalSessions();

    @Query("""
SELECT COALESCE(SUM(g.totalAmount),0)
FROM GameSession g
WHERE DATE(g.endTime)=:today
AND g.paymentDone=true
""")
    Double getTodayRevenue(@Param("today") LocalDate today);

    @Query("""
SELECT COUNT(g)
FROM GameSession g
WHERE DATE(g.endTime)=:today
""")
    Long getTodaySessions(@Param("today") LocalDate today);

    @Query("""
SELECT COUNT(DISTINCT g.customer.id)
FROM GameSession g
WHERE g.businessDay.id = :businessDayId
AND g.paymentDone = true
""")
    Long countUniqueCustomers(@Param("businessDayId") Long businessDayId);


    @Query("""
SELECT COUNT(g)
FROM GameSession g
WHERE g.status = 'RUNNING'
""")
    Long countRunningSessions();




    @Query("""
SELECT COUNT(g)
FROM GameSession g
WHERE g.status = 'PAYMENT_PENDING'
""")
    Long countPaymentPendingSessions();


    @Query("""
SELECT COUNT(g)
FROM GameSession g
WHERE g.businessDay.id = :businessDayId
AND g.status = 'COMPLETED'
""")
    Long countCompletedSessions(@Param("businessDayId") Long businessDayId);


    @Query("""
SELECT g
FROM GameSession g
WHERE g.businessDay.id = :businessDayId
ORDER BY
CASE
WHEN g.status='RUNNING' THEN 1
WHEN g.status='PAYMENT_PENDING' THEN 2
ELSE 3
END,
g.id DESC
""")
    List<GameSession> getTodaySessions(@Param("businessDayId") Long businessDayId);

    @Query("""
SELECT g
FROM GameSession g
WHERE g.businessDay.id = :businessDayId
AND g.status = 'COMPLETED'
ORDER BY g.endTime DESC
""")
    List<GameSession> getCompletedSessionsForExcel(
            @Param("businessDayId") Long businessDayId
    );

}