package com.ccuk.demo.feature;

public enum FeatureFlag {

    CREATE_INSTRUCTION("create.instructions"), GET_INSTRUCTION_BY_ID("get.instruction.by.id"), GET_INSTRUCTION_BY_ASSIGNEE("get.instruction.by.assignee"), TEST_ENDPOINT("test.endpoint");

    String key;

    FeatureFlag(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
