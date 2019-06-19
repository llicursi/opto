package com.llicursi.opto.service;

import com.llicursi.opto.auth.AuthUserDetail;
import com.llicursi.opto.dataaccessobject.UserRepository;
import com.llicursi.opto.domainobject.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("User {} started authentication " , username);
        UserDO userByEmail = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException( "User " + username + " does not exist"));
        AuthUserDetail authUserDetail = new AuthUserDetail(userByEmail);
        LOGGER.info("User {} authenticate : {} " , username, authUserDetail.toString());
        return authUserDetail;
    }

}
