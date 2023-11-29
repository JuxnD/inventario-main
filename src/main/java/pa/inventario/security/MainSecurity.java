package pa.inventario.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pa.inventario.security.jwt.JwtEntryPoint;
import pa.inventario.security.jwt.JwtTokenFilter;
import pa.inventario.security.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity


public class MainSecurity extends WebSecurityConfiguration {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;
    @Autowired
    JwtEntryPoint jwtEntryPoint;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    AuthenticationManager authenticationManager;
    @Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsServiceimpl).passwordEncoder(passwordEncoder);
        authenticationManager = builder.build();
        http.authenticationManager(authenticationManager);

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().requestMatchers("/auth/**",
        "/email-password/**",
                "/v2/api-docs/**",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/configuration/**").permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }

}
