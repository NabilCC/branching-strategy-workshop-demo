package com.ccuk.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    @GetMapping("/")
    public String testEndpoint() {
        LOG.info("Test endpoint called");
        return "It works!";
    }


}