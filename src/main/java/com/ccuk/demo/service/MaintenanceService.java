package com.ccuk.demo.service;

import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.exception.ValidationException;
import com.ccuk.demo.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public MaintenanceInstruction saveMaintenanceInstruction(MaintenanceInstruction maintenanceInstruction) throws ValidationException {
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

        return maintenanceRepository.create(maintenanceInstruction);
    }


}
