package com.ccuk.demo.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeatureFlagConfig {

    private FeatureFlag flag;
    private boolean enabled;
    private Set<String> permittedRoles;

    private FeatureFlagConfig() {
    }

    public static FeatureFlagConfig enabled(FeatureFlag flag, Set<String> permittedRoles) {
        FeatureFlagConfig config = new FeatureFlagConfig();
        config.flag = flag;
        config.permittedRoles = permittedRoles;
        config.enabled = true;
        return config;
    }

    public static FeatureFlagConfig disabled(FeatureFlag flag) {
        FeatureFlagConfig config = new FeatureFlagConfig();
        config.flag = flag;
        config.permittedRoles = new HashSet<>();
        config.enabled = false;
        return config;
    }

    public static FeatureFlagConfig of(FeatureFlag flag, boolean enabled, Set<String> permittedRoles) {
        FeatureFlagConfig config = new FeatureFlagConfig();
        config.flag = flag;
        config.permittedRoles = permittedRoles;
        config.enabled = enabled;
        return config;
    }

    public FeatureFlag getFlag() {
        return flag;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getPermittedRoles() {
        return permittedRoles;
    }
}
