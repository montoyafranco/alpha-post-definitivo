package com.posada.santiago.alphapostsandcomments.application.config;



import com.posada.santiago.alphapostsandcomments.application.adapters.repository.IUserRepository;
import com.posada.santiago.alphapostsandcomments.application.config.jwt.JwtTokenAuthenticationFilter;
import com.posada.santiago.alphapostsandcomments.application.config.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityAccess(ServerHttpSecurity httpSecurity,
                                                JwtTokenProvider tokenProvider,
                                                ReactiveAuthenticationManager reactiveAuthenticationManager,
                                                CorsConfigurationSource corsConfigurationSource
    ) {
        //Define as constants the endpoints that you have
        final String CREATE_POST = "/create/post";
        final String CREATE_USERS ="/auth/save/**";
        final String CREATE_COMMENT = "/create/comment";

        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .cors().configurationSource(corsConfigurationSource).and()
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange( access -> access
                        .pathMatchers(CREATE_POST).hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .pathMatchers(CREATE_USERS).hasAuthority("ROLE_ADMIN")
                        .pathMatchers(CREATE_COMMENT).hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .anyExchange().permitAll()
                ).addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();

    }
    @Bean
    public ReactiveUserDetailsService userDetailsService(IUserRepository users) {
        return username -> users.findByUsername(username)
                .map(u -> User
                        .withUsername(u.getUsername()).password(u.getPassword())
                        .authorities(u.getRoles().toArray(new String[0]))
                        .accountExpired(!u.isActive())
                        .credentialsExpired(!u.isActive())
                        .disabled(!u.isActive())
                        .accountLocked(!u.isActive())
                        .build()
                );
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder){
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //There is no PasswordEncoder mapped for the id "null"
        // return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
