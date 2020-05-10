package com.ccuk.demo.feature;

public enum FeatureFlag {

    TEST_ENDPOINT("test.endpoint");

    String key;

    FeatureFlag(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
