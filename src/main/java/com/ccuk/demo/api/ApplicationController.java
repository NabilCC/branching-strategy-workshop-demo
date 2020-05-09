package com.ccuk.demo.api;

import com.ccuk.demo.service.MaintenanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    @Value("${welcome.message}")
    private String welcomeMessage;

    private MaintenanceService maintenanceService;

    @Autowired
    public ApplicationController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/")
    public String testEndpoint(Principal principal) {
        LOG.info("Test endpoint called");
        return maintenanceService.welcomeMessage();
    }

}
