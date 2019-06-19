package com.llicursi.opto.auth;

import com.llicursi.opto.domainobject.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class AuthUtilsTest {

    @Test
    public void givenParseAuthorities_whenValidRoles_thenReturnPrefixedAuthorities(){
        List<SimpleGrantedAuthority> authorities = AuthUtils.parseAuthorities("USER,VIEWER,OWNER");
        for (SimpleGrantedAuthority authority : authorities){
            assertThat(authority.getAuthority()).startsWith("ROLE_");
        }
    }

    @Test
    public void givenParseAuthorities_whenThreeValidSpacedRoles_thenReturnTrimmedAuthorities(){
        List<SimpleGrantedAuthority> authorities = AuthUtils.parseAuthorities(" USER , VIEWER   , OWNER ");
        assertThat(authorities.size()).isEqualTo(3);
        assertThat(authorities.get(0).getAuthority()).isEqualTo("ROLE_USER");
        assertThat(authorities.get(1).getAuthority()).isEqualTo("ROLE_VIEWER");
        assertThat(authorities.get(2).getAuthority()).isEqualTo("ROLE_OWNER");
    }

    @Test
    public void givenParseAuthorities_whenRolesWithSpecialChar_thenReturnOnlyRolesWithoutSpecialChar(){
        List<SimpleGrantedAuthority> authorities = AuthUtils.parseAuthorities(" USER , VIEW-ER   , $OWNER ");
        assertThat(authorities.size()).isEqualTo(1);
        assertThat(authorities.get(0).getAuthority()).isEqualTo("ROLE_USER");
    }


}
