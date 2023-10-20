package kt.warmup.musicdb.config

import kt.warmup.musicdb.services.AccountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfiguration(
        val apiAuthFilter: ApiAuthFilter,
        val accountService: AccountService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                // I consider it's safe since all requests authorized by token
                .csrf {it.disable()}
                .authorizeHttpRequests {
                    it.requestMatchers(AntPathRequestMatcher("/api/v1/key/*")).permitAll()
                    it.requestMatchers(AndRequestMatcher(
                            AntPathRequestMatcher("/api/v1/**"),
                            RequestMatcher { it.method == "GET" }
                    )).permitAll()
                    it.requestMatchers(AndRequestMatcher(
                            AntPathRequestMatcher("/api/v1/**"),
                            RequestMatcher { it.method != "GET" }
                    )).authenticated()
                    it.anyRequest().authenticated()
                }
                .sessionManagement{
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .addFilterBefore(apiAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
                .httpBasic {}
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService = accountService

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}