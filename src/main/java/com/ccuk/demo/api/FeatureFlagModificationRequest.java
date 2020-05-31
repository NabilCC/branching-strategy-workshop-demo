package com.ccuk.demo.api;

import com.ccuk.demo.feature.FeatureFlag;

import java.util.List;
import java.util.Set;

public class FeatureFlagModificationRequest {

    private String flag;
    private boolean enabled;
    private List<String> authorities;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
