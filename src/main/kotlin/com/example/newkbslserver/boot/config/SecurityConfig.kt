package com.example.newkbslserver.boot.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
class SecurityConfig(
    private val corsConfig: CorsConfig,
//    private val authJwtFilter: AuthJwtFilter
) {

    private val PERMIT_URL_ARRAY = arrayOf(
        /* swagger v2 */
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        /* swagger v3 */
        "/v3/api-docs/**",
        "/swagger-ui/**"
    )

    @Bean
    @Throws(java.lang.Exception::class)
    protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf { csrf -> csrf.disable() }
            .oauth2Login{ oauth2Login -> oauth2Login.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors{ cors -> cors.configurationSource(corsConfig.corsFilter())}
            .httpBasic { httpBasic -> httpBasic.disable() }
            .authorizeHttpRequests { authz -> authz
                .requestMatchers(*PERMIT_URL_ARRAY).permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/**/auth/**").permitAll()
                .requestMatchers("/**/test/**").permitAll()
                .requestMatchers("/**/adm/**").hasAnyRole("ADMIN")
                .requestMatchers("/**/rank/**").permitAll()
                .requestMatchers("/**/league/**").permitAll()
                .requestMatchers("/**/score/**").permitAll()
                .requestMatchers("/**/user/**").permitAll()
                .requestMatchers("/**/song/**").permitAll()
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/resources/**") }
    }
}