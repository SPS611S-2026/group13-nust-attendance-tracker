package com.nust.attendance.service;

import com.nust.attendance.dto.ModuleDTO;
import com.nust.attendance.model.Module;
import com.nust.attendance.repository.EnrollmentRepository;
import com.nust.attendance.repository.ModuleRepository;
import com.nust.attendance.repository.SessionRepository;
import com.nust.attendance.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepo;
    private final UserRepository userRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final SessionRepository sessionRepo;

    public ModuleService(ModuleRepository moduleRepo, UserRepository userRepo,
                         EnrollmentRepository enrollmentRepo, SessionRepository sessionRepo) {
        this.moduleRepo = moduleRepo;
        this.userRepo = userRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.sessionRepo = sessionRepo;
    }

    public List<ModuleDTO> getModulesForLecturer(Long lecturerId) {
        return moduleRepo.findByLecturer_UserId(lecturerId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ModuleDTO> getAllModules() {
        return moduleRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ModuleDTO getById(Long id) {
        return toDTO(moduleRepo.findById(id).orElseThrow(() -> new RuntimeException("Module not found.")));
    }

    public Module getEntityById(Long id) {
        return moduleRepo.findById(id).orElseThrow(() -> new RuntimeException("Module not found: " + id));
    }

    private ModuleDTO toDTO(Module m) {
        long studentCount = enrollmentRepo.findByModule_ModuleId(m.getModuleId()).size();
        long sessionCount = sessionRepo.countByModule_ModuleId(m.getModuleId());
        return ModuleDTO.builder()
                .moduleId(m.getModuleId())
                .moduleCode(m.getModuleCode())
                .moduleName(m.getModuleName())
                .lecturerName(m.getLecturer().getFullName())
                .lecturerId(m.getLecturer().getUserId())
                .studentCount((int) studentCount)
                .sessionCount((int) sessionCount)
                .build();
    }
}
