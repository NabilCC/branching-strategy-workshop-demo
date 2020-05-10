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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureFlagService implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(FeatureFlagService.class);

    @Value("${launch.darkly.key}")
    private String launchDarklyKey;

    private LDClient launchDarklyClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (launchDarklyKey == null || launchDarklyKey.isBlank()) {
            throw new RuntimeException("The Launch Darkly environment variable LD_KEY has not been set");
        }
        launchDarklyClient = new LDClient(launchDarklyKey);
        LOG.info("Created Launch Darkly client");
    }

    @Override
    public void destroy() throws Exception {
        launchDarklyClient.close();
    }

    public boolean isFeatureEnabledForUser(Principal userPrincipal, FeatureFlag flag) {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) userPrincipal;
        String username = userPrincipal.getName();
        List<String> authorityStrings = userToken.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast).map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LOG.debug("Is feature {} enabled for user: {}, with authorities: {}", flag.getKey(), username, authorityStrings);

        LDUser ldUser = new LDUser.Builder(username).customString("authorities", authorityStrings).build();

        boolean result = launchDarklyClient.boolVariation(flag.getKey(), ldUser, false);
        LOG.debug("Is feature {} enabled for user: {} result: {}", flag.getKey(), username, result);
        return result;
    }

}
