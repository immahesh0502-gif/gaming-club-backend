package com.mahesh.gamingclubmanagementsystem.controller;
import com.mahesh.gamingclubmanagementsystem.dto.MonthlyReportDTO;
import com.mahesh.gamingclubmanagementsystem.dto.ReportDTO;
import com.mahesh.gamingclubmanagementsystem.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import com.mahesh.gamingclubmanagementsystem.pdf.PdfReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.mahesh.gamingclubmanagementsystem.excel.ExcelReportService;
import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PdfReportService pdfReportService;

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping
    public ReportDTO getReport() {
        return reportService.getReport();
    }
    @GetMapping("/monthly")
    public MonthlyReportDTO getMonthlyReport(

            @RequestParam int year,
            @RequestParam int month

    ) {

        return reportService.getMonthlyReport(year, month);

    }
    @GetMapping("/daily")
    public ReportDTO getDailyReport(
            @RequestParam LocalDate date
    ) {

        return reportService.getDailyReport(date);

    }

    @GetMapping("/export/pdf")
    public ResponseEntity<InputStreamResource> exportPdf(
            @RequestParam LocalDate date
    ) {

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "inline; filename=CueFox_Daily_Report.pdf"
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(
                        new InputStreamResource(
                                pdfReportService.generateDailyReport(date)
                        )
                );

    }
    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportExcel(
            @RequestParam LocalDate date
    ) throws IOException {

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "attachment; filename=CueFox_Report.xlsx"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(
                        new InputStreamResource(
                                excelReportService.generateExcel(date)
                        )
                );
    }

}