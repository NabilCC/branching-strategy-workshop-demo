package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstruction;
import com.ccuk.demo.exception.EntityNotFoundException;
import com.ccuk.demo.exception.ValidationException;
import com.ccuk.demo.feature.FeatureFlag;
import com.ccuk.demo.feature.FeatureFlagService;
import com.ccuk.demo.service.MaintenanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
    public static final String INSTRUCTION_RESOURCE = "/instruction";
    public static final String FEATURE_NOT_ENABLED_FOR_USER = "This feature is not enabled for the specified user";

    private FeatureFlagService featureFlagService;
    private MaintenanceService maintenanceService;

    @Autowired
    public ApplicationController(FeatureFlagService featureFlagService, MaintenanceService maintenanceService) {
        this.featureFlagService = featureFlagService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/")
    public String testEndpoint(Principal principal) {
        boolean enabled = featureFlagService.isFeatureEnabledForUser(principal, FeatureFlag.TEST_ENDPOINT);
        return enabled ? maintenanceService.welcomeMessage() : FEATURE_NOT_ENABLED_FOR_USER;
    }

    public static String invalidRequestMessage(ValidationException e) {
        return "Invalid request: " + e.getMessage();
    }

}
