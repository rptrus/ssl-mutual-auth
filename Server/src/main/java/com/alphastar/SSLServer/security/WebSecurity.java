package com.alphastar.SSLServer.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${X509SubjectMatch}")
    private String valueToMatch;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String x509Subject = String.format("CN=localhost(.*%s.*)",valueToMatch);
        
        X509AuthenticatedUserDetailsService x509UserDetails = new X509AuthenticatedUserDetailsService();
        x509UserDetails.setMatch(valueToMatch);
       
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/bronze/person").denyAll()
                .antMatchers("/silver/person").permitAll()
                .and().authorizeRequests()
                .antMatchers("/gold/person").authenticated()
                .and().x509().subjectPrincipalRegex(x509Subject).authenticationUserDetailsService(x509UserDetails);

    }
}
