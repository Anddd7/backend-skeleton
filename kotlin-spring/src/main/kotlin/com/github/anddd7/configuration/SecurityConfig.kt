package com.github.anddd7.configuration

import com.github.anddd7.service.AuthorizationService
import com.github.anddd7.service.EnvironmentProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var authorizationService: AuthorizationService
    @Autowired
    private lateinit var environmentProvider: EnvironmentProvider

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/favicon.ico")
            .and()
            .debug(environmentProvider.notProduction())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authorizationService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests().mvcMatchers("/api/**").authenticated()
            .and()
            .logout()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }
}
