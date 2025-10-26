package org.example.tanedu.Security;


import org.example.tanedu.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/login").permitAll()
//                                .requestMatchers("/api/auth/register").hasAuthority("SYSADMIN")
//                                .requestMatchers("/api/auth/logout").hasAnyAuthority("STUDENT","TEACHER","SYSADMIN","CLASSLEADER")
//                                .requestMatchers("/api/auth/change-password").hasAnyAuthority("STUDENT","TEACHER","SYSADMIN","CLASSLEADER")
//
//                                .requestMatchers("/api/users/getAll").hasAnyAuthority("STUDENT","TEACHER","SYSADMIN","CLASSLEADER")
//                                .requestMatchers("/api/users/getById/*").hasAnyAuthority("STUDENT","TEACHER","SYSADMIN","CLASSLEADER")
//                                .requestMatchers("/api/users/*").hasAnyAuthority("STUDENT","TEACHER","SYSADMIN","CLASSLEADER")
//                                .requestMatchers("/api/users/delete/*").hasAuthority("SYSADMIN")
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // Live Server URL-je
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("**", config);
        return new CorsFilter(source);
    }
}
