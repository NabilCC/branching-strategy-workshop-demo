package com.ccuk.demo.repository;

import com.ccuk.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
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
    public static final String FINANCE_OPERATIVE = "finance_operative";
    public static final String FINANCE_LEADER = "finance_leader";

    //======================================================== Authority Constants ====================================================
    public static final SimpleGrantedAuthority APPLICATION_ACCESS = new SimpleGrantedAuthority("application_access");
    public static final SimpleGrantedAuthority MAINTENANCE_OPERATIONS_AUTHORITY = new SimpleGrantedAuthority("maintenance_operations");
    private static final SimpleGrantedAuthority MAINTENANCE_ADMIN_AUTHORITY = new SimpleGrantedAuthority("maintenance_admin");
    public static final SimpleGrantedAuthority FINANCE_OPERATIONS_AUTHORITY = new SimpleGrantedAuthority("finance_operations");
    private static final SimpleGrantedAuthority FINANCE_ADMIN_AUTHORITY = new SimpleGrantedAuthority("finance_admin");

    //============================================================= User Datasource ====================================================
    private static final Map<String, UserDetails> usersByUsername;
    static {
        usersByUsername = new HashMap<>();
        usersByUsername.put(MAINTENANCE_OPERATIVE, new User(MAINTENANCE_OPERATIVE, "letmoin", Set.of(APPLICATION_ACCESS, MAINTENANCE_OPERATIONS_AUTHORITY)));
        usersByUsername.put(MAINTENANCE_LEADER, new User(MAINTENANCE_LEADER, "letmlin", Set.of(APPLICATION_ACCESS, MAINTENANCE_OPERATIONS_AUTHORITY, MAINTENANCE_ADMIN_AUTHORITY)));
        usersByUsername.put(FINANCE_OPERATIVE, new User(FINANCE_OPERATIVE, "letfoin", Set.of(APPLICATION_ACCESS, FINANCE_OPERATIONS_AUTHORITY)));
        usersByUsername.put(FINANCE_LEADER, new User(FINANCE_LEADER, "letflin", Set.of(APPLICATION_ACCESS, FINANCE_OPERATIONS_AUTHORITY, FINANCE_ADMIN_AUTHORITY)));
    }

    //================================================================== Methods =======================================================
    public UserDetails findByUsername(String username) {
        return usersByUsername.get(username);
    }

}
