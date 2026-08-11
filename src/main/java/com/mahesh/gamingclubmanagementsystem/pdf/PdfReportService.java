package com.mahesh.gamingclubmanagementsystem.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.mahesh.gamingclubmanagementsystem.services.ReportService;

import com.mahesh.gamingclubmanagementsystem.dto.ReportDTO;
import java.time.LocalDate;

import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;

import com.mahesh.gamingclubmanagementsystem.repository.BusinessDayRepository;

import org.springframework.beans.factory.annotation.Autowired;



@Service
public class PdfReportService {

    @Autowired
    private ReportService reportService;



    @Autowired
    private BusinessDayRepository businessDayRepository;

    public ByteArrayInputStream generateDailyReport(LocalDate reportDate){

        Document document = new Document(PageSize.A4);

        ReportDTO report = reportService.getDailyReport(reportDate);

    BusinessDay businessDay = businessDayRepository
            .findByBusinessDate(reportDate)
            .orElseThrow(() -> new RuntimeException("Business Day Not Found"));

        System.out.println("========== PDF REPORT ==========");
        System.out.println("Revenue = " + report.getTodayRevenue());
        System.out.println("Sessions = " + report.getTodaySessions());
        System.out.println("Customers = " + report.getTodayCustomers());
        System.out.println("Cash = " + report.getTodayCashRevenue());
        System.out.println("UPI = " + report.getTodayUpiRevenue());
        System.out.println("Card = " + report.getTodayCardRevenue());
        System.out.println("===============================");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            Font title =
                    new Font(Font.HELVETICA, 20, Font.BOLD);

            Font heading =
                    new Font(Font.HELVETICA, 14, Font.BOLD);

            Font normal =
                    new Font(Font.HELVETICA, 12);

            Paragraph p1 = new Paragraph(
                    "CUEFOX\nDaily Business Report",
                    title
            );

            p1.setAlignment(Element.ALIGN_CENTER);

            document.add(p1);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Business Date : " + businessDay.getBusinessDate(),
                    normal
            ));

            document.add(new Paragraph(
                    "Business Day : #" + businessDay.getId(),
                    normal
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Revenue Summary",
                    heading
            ));

            document.add(new Paragraph(
                    "Today's Revenue : ₹" + report.getTodayRevenue(),
                    normal
            ));

            document.add(new Paragraph(
                    "Total Sessions : " + report.getTodaySessions(),
                    normal
            ));

            document.add(new Paragraph(
                    "Total Customers : " + report.getTodayCustomers(),
                    normal
            ));

            document.add(new Paragraph(
                    "Snooker Revenue : ",
                    normal
            ));

            document.add(new Paragraph(
                    "PS5 Revenue : ",
                    normal
            ));

            document.add(new Paragraph(
                    "Carrom Revenue : ",
                    normal
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Payment Summary",
                    heading
            ));

            document.add(new Paragraph(
                    "Cash : ₹" + report.getTodayCashRevenue(),
                    normal
            ));

            document.add(new Paragraph(
                    "UPI : ₹" + report.getTodayUpiRevenue(),
                    normal
            ));

            document.add(new Paragraph(
                    "Card : ₹" + report.getTodayCardRevenue(),
                    normal
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Resource Usage",
                    heading
            ));

            document.add(new Paragraph(
                    "Tables Played : ",
                    normal
            ));

            document.add(new Paragraph(
                    "PS5 Sessions : ",
                    normal
            ));

            document.add(new Paragraph(
                    "Carrom Sessions : ",
                    normal
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(" ", normal));

            document.add(new Paragraph(
                    "Average Bill : ₹" + String.format("%.2f", report.getAverageBill()),
                    normal
            ));

            document.add(new Paragraph(" ", normal));

            document.add(new Paragraph(
                    "Generated by CueFox Gaming Club Management System",
                    heading
            ));

            document.close();

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return new ByteArrayInputStream(out.toByteArray());

    }

}