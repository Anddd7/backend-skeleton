package com.github.anddd7.security

import com.github.anddd7.core.service.EnvironmentProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile("local")
class SecurityConfig : WebSecurityConfigurerAdapter() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userDetailsService: UserDetailsService
    @Autowired
    private lateinit var environmentProvider: EnvironmentProvider

    private val jwtConfig = JWTConfig().apply {
        log.debug(
            "Generate key pair: \n{}\n{}",
            this.keyPair.publicKeyString,
            this.keyPair.privateKeyString
        )
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/favicon.ico")
            .and()
            .debug(environmentProvider.notProduction())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests().mvcMatchers("/api/**")
            .authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(jwtConfig, authenticationManager()))
            .addFilter(JWTAuthorizationFilter(jwtConfig, authenticationManager()))
            .logout()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManager(): AuthenticationManager = super.authenticationManager()
}
