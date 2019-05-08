package com.github.anddd7.security

class JWTConfig(
    privateKeyString: String = "",
    publicKeyString: String = ""
) {
    val authUrl = "/auth/authenticate"
    val expirationTime = 864_000_000
    val algorithm = "RS512"
    val keyPair = RSAKeyPairProvider.build(privateKeyString, publicKeyString)

    companion object {
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }
}