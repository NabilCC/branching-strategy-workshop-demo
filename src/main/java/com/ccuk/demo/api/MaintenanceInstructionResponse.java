package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstructionType;

import java.util.Date;

public class MaintenanceInstructionResponse {

    private Long id;
    private String assignee;
    private String address;
    private MaintenanceInstructionType type;
    private Date completionByDate;

    public Long getId() {
        return id;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getAddress() {
        return address;
    }

    public MaintenanceInstructionType getType() {
        return type;
    }

    public Date getCompletionByDate() {
        return completionByDate;
    }
}
