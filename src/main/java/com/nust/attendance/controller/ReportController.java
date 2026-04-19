package com.nust.attendance.controller;

import com.nust.attendance.dto.StudentReportDTO;
import com.nust.attendance.service.AttendanceService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final AttendanceService attendanceService;

    public ReportController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * GET /api/reports/module/{moduleId}/excel
     * Returns an Excel file with attendance data for the module.
     */
    @GetMapping("/module/{moduleId}/excel")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<byte[]> exportExcel(@PathVariable Long moduleId) throws Exception {
        List<StudentReportDTO> report = attendanceService.getModuleReport(moduleId);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Attendance Report");

            // Header row
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] headers = {"Student Number", "Full Name", "Total Sessions",
                                 "Present", "Absent", "Attendance %", "Status"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Data rows
            int rowNum = 1;
            for (StudentReportDTO dto : report) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dto.getStudentNumber());
                row.createCell(1).setCellValue(dto.getFullName());
                row.createCell(2).setCellValue(dto.getTotalSessions());
                row.createCell(3).setCellValue(dto.getSessionsAttended());
                row.createCell(4).setCellValue(dto.getSessionsAbsent());
                row.createCell(5).setCellValue(dto.getAttendancePercentage() + "%");
                row.createCell(6).setCellValue(dto.isBelowThreshold() ? "BELOW 80%" : "OK");
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance_report.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }

    /**
     * GET /api/reports/module/{moduleId}/csv
     * Simple CSV fallback if iText is not available.
     */
    @GetMapping("/module/{moduleId}/csv")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<byte[]> exportCsv(@PathVariable Long moduleId) {
        List<StudentReportDTO> report = attendanceService.getModuleReport(moduleId);

        StringBuilder sb = new StringBuilder();
        sb.append("Student Number,Full Name,Total Sessions,Present,Absent,Attendance %,Status\n");
        for (StudentReportDTO dto : report) {
            sb.append(String.format("%s,%s,%d,%d,%d,%.1f%%,%s\n",
                    dto.getStudentNumber(), dto.getFullName(),
                    dto.getTotalSessions(), dto.getSessionsAttended(), dto.getSessionsAbsent(),
                    dto.getAttendancePercentage(),
                    dto.isBelowThreshold() ? "BELOW 80%" : "OK"));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance_report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(sb.toString().getBytes());
    }
}
