package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstructionType;

import java.util.Date;

public class MaintenanceInstructionResponse {

    private Long id;
    private String assignee;
    private String address;
    private MaintenanceInstructionType type;
    private Date completionByDate;

    public MaintenanceInstructionResponse() {
        // for Jackson
    }

    public MaintenanceInstructionResponse(Long id, String assignee, String address, MaintenanceInstructionType type, Date completionByDate) {
        this.id = id;
        this.assignee = assignee;
        this.address = address;
        this.type = type;
        this.completionByDate = completionByDate;
    }

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

    public void setId(Long id) {
        this.id = id;
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
}
