package com.ccuk.demo.entity;

import java.util.Date;

public class MaintenanceInstruction {

    private Long id;
    private String assignee;
    private String address;
    private MaintenanceInstructionType type;
    private Date completionByDate;

    public MaintenanceInstruction(String assignee, String address, MaintenanceInstructionType type, Date completionByDate) {
        this.assignee = assignee;
        this.address = address;
        this.type = type;
        this.completionByDate = completionByDate;
    }

    public MaintenanceInstruction() {
        // For Jackson
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MaintenanceInstructionType getType() {
        return type;
    }

    public void setType(MaintenanceInstructionType type) {
        this.type = type;
    }

    public Date getCompletionByDate() {
        return completionByDate;
    }

    public void setCompletionByDate(Date completionByDate) {
        this.completionByDate = completionByDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
