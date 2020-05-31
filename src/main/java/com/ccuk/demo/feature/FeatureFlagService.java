package com.ccuk.demo.feature;

import com.launchdarkly.client.LDClient;
import com.launchdarkly.client.LDUser;
import com.launchdarkly.client.value.LDValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeatureFlagService implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(FeatureFlagService.class);
    private Map<FeatureFlag, FeatureFlagConfig> datasource;

    @Override
    public void afterPropertiesSet() throws Exception {
        datasource = new HashMap<>();
        for (FeatureFlag value : FeatureFlag.values()) {
            datasource.put(value, FeatureFlagConfig.disabled(value));
        }
    }
    public boolean isFeatureEnabledForUser(Principal userPrincipal, FeatureFlag flag) {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) userPrincipal;
        String username = userPrincipal.getName();
        Set<String> authorityStrings = userToken.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast).map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        LOG.debug("Is feature {} enabled for user: {}, with authorities: {}", flag.getKey(), username, authorityStrings);

        FeatureFlagConfig config = datasource.get(flag);
        if (config.getPermittedRoles().isEmpty()) {
            return config.isEnabled();
        }
        Set<String> intersection = new HashSet<>(config.getPermittedRoles());
        intersection.retainAll(authorityStrings);
        boolean enabled = config.isEnabled() && intersection.size() > 0;
        LOG.debug("Is feature {} enabled for user: {} result: {}", flag.getKey(), username, enabled);
        return enabled;
    }

    public List<FeatureFlagConfig> getAllFeatures() {
        return new ArrayList<>(datasource.values());
    }

    public void setConfigForFlag(FeatureFlag flag, boolean enabled, List<String> roles) {
        datasource.put(flag, FeatureFlagConfig.of(flag, enabled, roles == null ? new HashSet<>() : new HashSet<>(roles)));
    }

}
