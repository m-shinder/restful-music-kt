package kt.warmup.musicdb

import org.springframework.security.core.GrantedAuthority

data class MusicdbGrantedAuthority(
        val action: AuthorityAction,
        val target: String,
): GrantedAuthority {

    override fun getAuthority(): String {
        return action.toString() + "_OVER_" + target
    }

    companion object {
        // constructor from string
        operator fun invoke(string: String): GrantedAuthority {
            val split = string.split("_")
            return MusicdbGrantedAuthority(AuthorityAction.valueOf(split.first()), split.last())
        }

        fun allOver(target: String) = AuthorityAction.values().map { MusicdbGrantedAuthority(it, target) }
    }

    enum class AuthorityAction {
        CREATE,
        READ,
        UPDATE,
        DELETE,
    }
}
