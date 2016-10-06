package com.github.aint.habraclone.web.config;

import com.github.aint.habraclone.data.model.Role;
import com.github.aint.habraclone.service.config.ServiceSpringConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

@EnableWebSecurity
@Import({ DispatcherSpringConfig.class })
public class SecuritySpringConfig {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final AuthenticationEntryPoint authenticationEntryPoint;
        private final AccessDeniedHandler accessDeniedHandler;
        private final GenericFilterBean tokenAuthenticationFilter;

        public ApiWebSecurityConfigurationAdapter(AuthenticationEntryPoint authenticationEntryPoint,
                                                  AccessDeniedHandler accessDeniedHandler,
                                                  GenericFilterBean tokenAuthenticationFilter) {
            this.authenticationEntryPoint = authenticationEntryPoint;
            this.accessDeniedHandler = accessDeniedHandler;
            this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .and()
                    .authorizeRequests()
                            .antMatchers("/api/authenticate").permitAll()
                            .antMatchers("/api/articles/**").permitAll()
                            .antMatchers("/api/users/**").permitAll()
                            .antMatchers("/api/comments/add").authenticated()
                            .antMatchers("/api/admin/**").hasAuthority(Role.ADMIN.name())
                            .and()
                    .exceptionHandling()
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
                            .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Import({ ServiceSpringConfig.class })
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userTransactionalService;
        private final AuthenticationSuccessHandler successfulAuthenticationHandler;

        public FormLoginWebSecurityConfigurerAdapter(UserDetailsService userTransactionalService,
                                                     AuthenticationSuccessHandler successfulAuthenticationHandler
        ) {
            this.userTransactionalService = userTransactionalService;
            this.successfulAuthenticationHandler = successfulAuthenticationHandler;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                            .antMatchers("/resources/**", "/login").permitAll()
                            .antMatchers("/articles/add", "/comments/add").authenticated()
                            .antMatchers("/articles/**/vote/plus", "/articles/**/vote/minus").authenticated()
                            .antMatchers("/users/**/ban").hasAuthority(Role.ADMIN.name())
                            .antMatchers("/users/**/unban").hasAuthority(Role.ADMIN.name())
                            .antMatchers("/articles/**/delete").hasAuthority(Role.ADMIN.name())
                            .antMatchers("/comments/**/delete").hasAuthority(Role.ADMIN.name())
                            .and()
                    .formLogin()
                            .loginPage("/login").permitAll()
                            .successHandler(successfulAuthenticationHandler)
                            .and()
                    .logout()
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true)
                            .deleteCookies("remember-me")
                            .and()
                    .rememberMe()
                            .key("somehashcode")
                            .tokenValiditySeconds(86400)
                            .userDetailsService(userTransactionalService);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userTransactionalService).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

}
