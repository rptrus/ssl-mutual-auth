package com.alphastar.SSLServer.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;

@Component
public class X509AuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String partialSubject;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
            throws UsernameNotFoundException {
        logger.info("Entered loadUserDetails(...)");
        X509Certificate certificate = (X509Certificate)token.getCredentials();
        if (!certificate.getSubjectX500Principal().getName().contains(partialSubject)) {
            logger.warn("This is an unknown / unexpected cert");
        }

        Collection<GrantedAuthority> authorities = Collections.EMPTY_LIST;
        return  new User(certificate.getSubjectX500Principal().getName(), "(un-necessary for certificate validation)", authorities);
    }
    
    public void setMatch(String partialSubject) {
    	this.partialSubject  = partialSubject;
    }
}
