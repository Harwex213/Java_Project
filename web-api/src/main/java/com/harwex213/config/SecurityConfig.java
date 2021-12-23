package com.harwex213.config;

import com.harwex213.filter.JwtTokenFilter;
import com.harwex213.models.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()))
                .and();

        http.authorizeRequests()
                // admin endpoints
                .antMatchers(HttpMethod.POST, "/api/movies").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/movies").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/movies").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/cinemas").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/cinemas").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/cinemas").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/cinema-movies").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/cinema-movies").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/sessions").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/sessions").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/sessions").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/tickets").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/tickets").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/api/tickets").hasAuthority("USER")

                .antMatchers(HttpMethod.POST, "/api/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users").hasAuthority("ADMIN")
                // others endpoints are public
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}