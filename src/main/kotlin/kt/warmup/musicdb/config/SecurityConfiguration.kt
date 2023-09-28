package kt.warmup.musicdb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf {}
                .authorizeHttpRequests {
                    it.requestMatchers(AntPathRequestMatcher("/api/v1/key/register")).permitAll()
                    it.requestMatchers(AntPathRequestMatcher("/api/v1/key/register")).permitAll()
                    it.requestMatchers(AndRequestMatcher(
                            AntPathRequestMatcher("/api/v1/**"),
                            RequestMatcher { it.method == "GET" }
                    )).permitAll()
                    it.requestMatchers(AndRequestMatcher(
                            AntPathRequestMatcher("/api/v1/**"),
                            RequestMatcher { it.method != "GET" }
                    )).authenticated()
                    it.anyRequest().denyAll()
                }
                .httpBasic {}
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
        // TODO: Actuall user fetching
        return UserDetailsService { username ->  user.build() }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}