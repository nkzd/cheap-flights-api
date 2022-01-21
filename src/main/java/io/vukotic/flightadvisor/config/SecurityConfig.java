package io.vukotic.flightadvisor.config;

import io.vukotic.flightadvisor.filters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        To enable h2 console
//        http.headers().frameOptions().disable();

        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                var config = new CorsConfiguration();
                config.setAllowedOrigins(
                        corsConfig.getAllowedOrigins());
                config.setAllowedMethods(
                        corsConfig.getAllowedMethods());
                return config;
            };
            c.configurationSource(source);
        });

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/cities").hasRole("ADMIN")
                .mvcMatchers("/airports").hasRole("ADMIN")
                .mvcMatchers("/routes").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/cities").hasRole("REGULAR_USER")
                .mvcMatchers("/cities/*/comments/*").hasRole("REGULAR_USER")
                .mvcMatchers("/comments/*").hasRole("REGULAR_USER")
                .mvcMatchers("/travel/*/*").hasRole("REGULAR_USER")
                .mvcMatchers("/users/login").permitAll()
                .mvcMatchers("/users/register").permitAll()
                .mvcMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
    }

}
