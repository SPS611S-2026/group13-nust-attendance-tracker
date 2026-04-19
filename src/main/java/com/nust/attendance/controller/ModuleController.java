package com.nust.attendance.controller;

import com.nust.attendance.dto.ModuleDTO;
import com.nust.attendance.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<List<ModuleDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @GetMapping("/lecturer/{lecturerId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<List<ModuleDTO>> getModulesForLecturer(@PathVariable Long lecturerId) {
        return ResponseEntity.ok(moduleService.getModulesForLecturer(lecturerId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN','STUDENT')")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getById(id));
    }
}
