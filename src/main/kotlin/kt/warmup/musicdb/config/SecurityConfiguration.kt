package kt.warmup.musicdb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
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
class SecurityConfiguration(
        val apiAuthFilter: ApiAuthFilter,
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