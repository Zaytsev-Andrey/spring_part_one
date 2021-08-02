package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserAuthService userAuthService) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("password"))
                .roles("SUPER_ADMIN")
                .and()
                .withUser("mem_guest")
                .password(passwordEncoder.encode("password"))
                .roles("GUEST");

        auth.userDetailsService(userAuthService);
    }

    @Configuration
    public static class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .antMatchers("/**/*.css", "/**/*.js").permitAll()
                        .antMatchers("/product", "/login").permitAll()
                        .antMatchers("/user/new").hasAnyRole("ANONYMOUS", "SUPER_ADMIN")
                        .antMatchers("/user").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .antMatchers("/user/update").hasAnyRole("ANONYMOUS", "SUPER_ADMIN")
                        .antMatchers("/product/**", "/user/**").hasAnyRole("SUPER_ADMIN")
                        .antMatchers(HttpMethod.POST, "/user/update").hasAnyRole("ANONYMOUS", "SUPER_ADMIN")
                        .antMatchers(HttpMethod.POST, "/product").hasRole("SUPER_ADMIN")
                        .antMatchers(HttpMethod.DELETE, "user/**", "/product/**").hasRole("SUPER_ADMIN")
                        .anyRequest().hasRole("SUPER_ADMIN")
                    .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/product")
                    .and()
                    .exceptionHandling()
                        .accessDeniedPage("/access_denied");
        }
    }
}
