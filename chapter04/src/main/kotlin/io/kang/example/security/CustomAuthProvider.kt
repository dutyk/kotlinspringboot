package io.kang.example.security

import io.kang.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class CustomAuthProvider: AuthenticationProvider {
    @Autowired
    lateinit var userRepository: UserRepository

    private val auth = mapOf(Pair("test03", "ROLE_USER"), Pair("test02", "ROLE_ADMIN"))

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val username = authentication.name
        val password = authentication.credentials.toString()

        val user = userRepository.findByUserName(username)

        if(user?.password.equals(password)) {
            val authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(auth[username])
            return UsernamePasswordAuthenticationToken(user, password, authorities)
        }

        return null
    }

    override fun supports(p0: Class<*>?): Boolean {
        return true;
    }
}