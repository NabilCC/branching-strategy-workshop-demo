package com.ccuk.demo.repository;

import com.ccuk.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    //===================================================== Username Constants =======================================================
    public static final String MAINTENANCE_OPERATIVE = "maintenance_operative";
    public static final String MAINTENANCE_LEADER = "maintenance_leader";
    public static final String SYSADMIN = "sysadmin";

    //======================================================== Authority Constants ====================================================
    public static final SimpleGrantedAuthority APPLICATION_ACCESS = new SimpleGrantedAuthority("application_access");
    public static final SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority("admin");
    private static final SimpleGrantedAuthority MAINTENANCE_ADMIN_AUTHORITY = new SimpleGrantedAuthority("maintenance_admin");
    public static final SimpleGrantedAuthority MAINTENANCE_OPERATIONS = new SimpleGrantedAuthority("maintenance_operations");

    //============================================================= User Datasource ====================================================
    private static final Map<String, UserDetails> usersByUsername;
    static {
        usersByUsername = new HashMap<>();
        usersByUsername.put(SYSADMIN, new User(SYSADMIN, "letadmin", Set.of(APPLICATION_ACCESS, ADMIN)));
        usersByUsername.put(MAINTENANCE_OPERATIVE, new User(MAINTENANCE_OPERATIVE, "letmoin", Set.of(APPLICATION_ACCESS, MAINTENANCE_OPERATIONS)));
        usersByUsername.put(MAINTENANCE_LEADER, new User(MAINTENANCE_LEADER, "letmlin", Set.of(APPLICATION_ACCESS, MAINTENANCE_ADMIN_AUTHORITY)));
    }

    //================================================================== Methods =======================================================
    public UserDetails findByUsername(String username) {
        return usersByUsername.get(username);
    }

}
