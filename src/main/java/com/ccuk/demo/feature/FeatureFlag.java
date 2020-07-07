package com.ccuk.demo.feature;

public enum FeatureFlag {

    CREATE_INSTRUCTION("createInstructions"), GET_INSTRUCTION_BY_ID("getInstructionById");

    String key;

    FeatureFlag(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
