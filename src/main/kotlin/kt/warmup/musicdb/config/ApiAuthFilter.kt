package kt.warmup.musicdb.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kt.warmup.musicdb.services.APIKeyService
import kt.warmup.musicdb.services.AccountService
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class ApiAuthFilter(
        val apiKeyService: APIKeyService,
        val accountService: AccountService,
        @Lazy
        val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {
        if (!request.headerNames.toList().contains("authorization")) {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("authorization")

        if (authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            val issuer = apiKeyService.issuerOf(token)

            val user = userDetailsService.loadUserByUsername(issuer)
            val auth = UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.authorities
            )
            SecurityContextHolder.getContext().authentication = auth
            filterChain.doFilter(request, response)
            return
        }

        filterChain.doFilter(request, response)
    }
}