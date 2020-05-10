package com.ccuk.demo.service;

import com.ccuk.demo.api.MaintenanceInstructionRequest;
import com.ccuk.demo.api.MaintenanceInstructionResponse;
import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.exception.EntityNotFoundException;
import com.ccuk.demo.exception.ValidationException;
import com.ccuk.demo.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MaintenanceService {

    public static final String ADDRESS_IS_MANDATORY = "Address is mandatory";
    public static final String ASSIGNEE_IS_MANDATORY = "Assignee is mandatory";
    public static final String COMPLETION_NOT_IN_PAST = "Completion by date must not be in the past";
    public static final String COMPLETION_BY_DATE_IS_MANDATORY = "Completion by date is mandatory";
    public static final String MAINTENANCE_TYPE_IS_MANDATORY = "Maintenance type is mandatory";

    private MaintenanceRepository maintenanceRepository;

    @Value("${welcome.message}")
    private String welcomeMessage;

    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public String welcomeMessage() {
        return welcomeMessage;
    }

    public MaintenanceInstruction createMaintenanceInstruction(MaintenanceInstructionRequest request) throws ValidationException {
        MaintenanceInstruction instruction = fromRequest(request);
        validateMaintenanceInstruction(instruction);
        return maintenanceRepository.create(instruction);
    }

    public MaintenanceInstruction updateMaintenanceInstruction(Long id, MaintenanceInstructionRequest request) throws ValidationException, EntityNotFoundException {
        MaintenanceInstruction instruction = fromRequest(request);
        validateMaintenanceInstruction(instruction);
        return maintenanceRepository.update(id, instruction);
    }

    public MaintenanceInstructionResponse toResponse(MaintenanceInstruction instruction) {
        MaintenanceInstructionResponse response = new MaintenanceInstructionResponse();
        response.setId(instruction.getId());
        response.setAddress(instruction.getAddress());
        response.setAssignee(instruction.getAssignee());
        response.setCompletionByDate(instruction.getCompletionByDate());
        response.setType(instruction.getType());
        return response;
    }

    public List<MaintenanceInstruction> findAll() {
        return maintenanceRepository.findAll();
    }

    public List<MaintenanceInstruction> findByAssignee(String assignee) {
        return maintenanceRepository.findByAssignee(assignee);
    }

    private void validateMaintenanceInstruction(MaintenanceInstruction maintenanceInstruction) throws ValidationException {
        Long nowEpochMs = new Date().getTime();

        if (maintenanceInstruction.getCompletionByDate() == null) {
            throw new ValidationException(COMPLETION_BY_DATE_IS_MANDATORY);
        }

        if (maintenanceInstruction.getCompletionByDate().getTime() < nowEpochMs) {
            throw new ValidationException(COMPLETION_NOT_IN_PAST);
        }

        if (maintenanceInstruction.getType() == null) {
            throw new ValidationException(MAINTENANCE_TYPE_IS_MANDATORY);
        }

        if (maintenanceInstruction.getAddress() == null) {
            throw new ValidationException(ADDRESS_IS_MANDATORY);
        }

        if (maintenanceInstruction.getAssignee() == null) {
            throw new ValidationException(ASSIGNEE_IS_MANDATORY);
        }
    }

    private MaintenanceInstruction fromRequest(MaintenanceInstructionRequest request) {
        return new MaintenanceInstruction(request.getAssignee(), request.getAddress(), request.getType(), request.getCompletionByDate());
    }

}
