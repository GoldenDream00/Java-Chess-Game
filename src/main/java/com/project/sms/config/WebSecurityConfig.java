package com.project.sms.config;

import com.project.sms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
    @Bean
    public UserDetailsService mongoUserDetails() {
        return new AuthService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserDetailsService userDetailsService = mongoUserDetails();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/articles/").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/events/**").permitAll()
                .antMatchers("/puzzle/").permitAll()
                .antMatchers("/students/create").hasAnyAuthority("ADMIN", "GUARDIAN")
                .antMatchers("/students/").hasAnyAuthority("ADMIN", "GUARDIAN")
                .antMatchers("/teachers/create").permitAll()
                .antMatchers("/teachers/").hasAuthority("ADMIN")
                .antMatchers("/guardians/create").permitAll()
                .antMatchers("/guardians/store").permitAll()
                .antMatchers("/students/store").permitAll()
                .antMatchers("/teachers/store").permitAll()
                .antMatchers("/guardians/").hasAuthority("ADMIN")
                .antMatchers("/attendance/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/puzzle/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/homework/*").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/homework/individual").hasAnyAuthority("GUARDIAN")
                .antMatchers("/articles/create", "/articles/update/*", "/articles/delete/*").hasAnyAuthority("ADMIN")
                .antMatchers("/events/create/*", "/events/update/*", "/events/delete/*").hasAnyAuthority("ADMIN")
                .antMatchers("/chess/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler)
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling();

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/scripts/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/webjars/**");
    }
}
