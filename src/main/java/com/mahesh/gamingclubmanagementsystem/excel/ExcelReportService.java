package com.mahesh.gamingclubmanagementsystem.excel;

import com.mahesh.gamingclubmanagementsystem.dto.ReportDTO;
import com.mahesh.gamingclubmanagementsystem.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import com.mahesh.gamingclubmanagementsystem.repository.BusinessDayRepository;
import com.mahesh.gamingclubmanagementsystem.repository.GameSessionRepository;
import com.mahesh.gamingclubmanagementsystem.entity.GameSession;

@Service
public class ExcelReportService {

    @Autowired
    private ReportService reportService;
    @Autowired
    private BusinessDayRepository businessDayRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    public ByteArrayInputStream generateExcel(LocalDate reportDate) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        ReportDTO report = reportService.getDailyReport(reportDate);

        var businessDay = businessDayRepository
                .findByBusinessDate(reportDate)
                .orElseThrow(() -> new RuntimeException("Business Day Not Found"));

        var sessions = gameSessionRepository
                .getCompletedSessionsForExcel(businessDay.getId());

        System.out.println("Business Day : " + businessDay.getId());
        System.out.println("Completed Sessions : " + sessions.size());

        Sheet sheet = workbook.createSheet("Daily Summary");
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Business Date");
        row1.createCell(1).setCellValue(reportDate.toString());

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("Total Revenue");
        row2.createCell(1).setCellValue(report.getTodayRevenue());

        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("Total Sessions");
        row3.createCell(1).setCellValue(report.getTodaySessions());

        Row row4 = sheet.createRow(4);
        row4.createCell(0).setCellValue("Total Customers");
        row4.createCell(1).setCellValue(report.getTodayCustomers());

        Row row5 = sheet.createRow(5);
        row5.createCell(0).setCellValue("Cash Revenue");
        row5.createCell(1).setCellValue(report.getTodayCashRevenue());

        Row row6 = sheet.createRow(6);
        row6.createCell(0).setCellValue("UPI Revenue");
        row6.createCell(1).setCellValue(report.getTodayUpiRevenue());

        Row row7 = sheet.createRow(7);
        row7.createCell(0).setCellValue("Card Revenue");
        row7.createCell(1).setCellValue(report.getTodayCardRevenue());

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Field");
        header.createCell(1).setCellValue("Value");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        Sheet sessionSheet = workbook.createSheet("Completed Sessions");

        Row sessionHeader = sessionSheet.createRow(0);

        sessionHeader.createCell(0).setCellValue("Session ID");
        sessionHeader.createCell(1).setCellValue("Customer");
        sessionHeader.createCell(2).setCellValue("Mobile");
        sessionHeader.createCell(3).setCellValue("Resource");
        sessionHeader.createCell(4).setCellValue("Players");
        sessionHeader.createCell(5).setCellValue("Start Time");
        sessionHeader.createCell(6).setCellValue("End Time");
        sessionHeader.createCell(7).setCellValue("Amount");
        sessionHeader.createCell(8).setCellValue("Payment");

        int rowNumber = 1;

        for (GameSession session : sessions) {

            Row row = sessionSheet.createRow(rowNumber++);

            row.createCell(0).setCellValue(session.getId());

            row.createCell(1).setCellValue(
                    session.getCustomer().getName()
            );

            row.createCell(2).setCellValue(
                    session.getCustomer().getMobileNumber()
            );

            row.createCell(3).setCellValue(
                    session.getResource().getName()
            );

            row.createCell(4).setCellValue(
                    session.getPlayerCount()
            );

            row.createCell(5).setCellValue(
                    session.getStartTime().toString()
            );

            row.createCell(6).setCellValue(
                    session.getEndTime().toString()
            );

            row.createCell(7).setCellValue(
                    session.getTotalAmount()
            );

            row.createCell(8).setCellValue(
                    session.getPaymentMethod() == null
                            ? "N/A"
                            : session.getPaymentMethod().toString()
            );


        }

        for (int i = 0; i <= 8; i++) {
            sessionSheet.autoSizeColumn(i);
        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

}