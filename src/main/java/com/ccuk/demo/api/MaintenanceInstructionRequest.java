package com.ccuk.demo.api;

import com.ccuk.demo.entity.MaintenanceInstructionType;

import java.util.Date;

public class MaintenanceInstructionRequest {

    private String assignee;
    private String address;
    private MaintenanceInstructionType type;
    private Date completionByDate;

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
