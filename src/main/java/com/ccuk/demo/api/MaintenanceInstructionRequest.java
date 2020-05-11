package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstructionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MaintenanceInstructionRequest {

    private String assignee;
    private String address;
    private MaintenanceInstructionType type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date completionByDate;

    public MaintenanceInstructionRequest() {
        // for Jackson
    }

    public MaintenanceInstructionRequest(String assignee, String address, MaintenanceInstructionType type, Date completionByDate) {
        this.assignee = assignee;
        this.address = address;
        this.type = type;
        this.completionByDate = completionByDate;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(MaintenanceInstructionType type) {
        this.type = type;
    }

    public void setCompletionByDate(Date completionByDate) {
        this.completionByDate = completionByDate;
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
