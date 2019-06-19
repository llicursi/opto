package com.llicursi.opto.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class AuthUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUtils.class);
    private static final String ROLE_SEPARATOR = ",";
    private static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";
    private static final String ROLE_REGEX = "^[A-Z0-9_]+$";

    /**
     * Converts a list of roles of separated by ',' into a list of granted authority
     * @param roles Text format roles with comma separator
     * @return List of Authorities
     */
    static List<SimpleGrantedAuthority> parseAuthorities(String roles) {
        LOGGER.debug("Parsing {} into the following Authorities", roles );
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roles == null){
            LOGGER.error("Empty ROLE list . Some user has no access" );
            return authorities;
        }

        String allRoles[] = roles.split(ROLE_SEPARATOR);
        for (String role : allRoles){
            String prefixedRole = formatToSpring(role);
            if (prefixedRole != null) {
                authorities.add(new SimpleGrantedAuthority(prefixedRole));
                LOGGER.debug(" -> {} ", formatToSpring(role) );
            } else {
                LOGGER.error("Invalid format role {}", role);
            }
        }
        return authorities;
    }

    private static String formatToSpring(String role) {
        String upperCaseRole = role.trim().toUpperCase();
        if (upperCaseRole.matches(ROLE_REGEX)){
            return SPRING_SECURITY_ROLE_PREFIX + upperCaseRole ;
        }
        return null;
    }

    /**
     * Extracts the user id from Authentication Principal
     * @param principal Session user details
     * @return User Id from AuthUserDetail
     */
    public static Long retrieveUserId(Principal principal){
        AuthUserDetail activeUser = (AuthUserDetail) ((Authentication) principal).getPrincipal();
        return activeUser.getId();
    }

}
