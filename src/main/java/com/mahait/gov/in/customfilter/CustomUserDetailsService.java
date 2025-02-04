package com.mahait.gov.in.customfilter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserByUsernameAndDomain(String username, int appCode) throws UsernameNotFoundException;

}
