package com.nust.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * NUST Digital Attendance Tracking System
 * SPS611S Software Processes — Group Project 2026
 */
@SpringBootApplication
@EnableScheduling  // enables scheduled tasks (e.g. daily low-attendance checks)
public class AttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }
}
