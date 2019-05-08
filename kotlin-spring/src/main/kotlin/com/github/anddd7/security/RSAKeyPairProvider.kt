package com.github.anddd7.security

import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

object RSAKeyPairProvider {
    fun build(
        privateKeyString: String,
        publicKeyString: String
    ) =
        if (privateKeyString.isBlank() || publicKeyString.isBlank()) buildRandomKeyPair()
        else retrieveKeyPair(privateKeyString, publicKeyString)

    private fun retrieveKeyPair(
        privateKeyString: String,
        publicKeyString: String
    ) = KeyPair(
        decodePrivateKey(privateKeyString),
        decodePublicKey(publicKeyString),
        privateKeyString,
        publicKeyString
    )

    private fun decodePrivateKey(privateKeyString: String) =
        KeyFactory.getInstance("RSA").generatePrivate(
            PKCS8EncodedKeySpec(
                decode(privateKeyString)
            )
        )

    private fun decodePublicKey(publicKeyString: String) =
        KeyFactory.getInstance("RSA").generatePublic(
            X509EncodedKeySpec(
                decode(publicKeyString)
            )
        )

    private fun buildRandomKeyPair(
    ): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(1024, SecureRandom())
        val keyPair = keyGen.generateKeyPair()
        val privateKey = keyPair.private as RSAPrivateKey
        val publicKey = keyPair.public as RSAPublicKey
        return KeyPair(privateKey, publicKey, privateKey.encode(), publicKey.encode())
    }

    private fun Key.encode() = Base64.getEncoder().encodeToString(this.encoded)

    private fun decode(keyString: String) = Base64.getDecoder().decode(keyString.toByteArray())
}

class KeyPair(
    val privateKey: PrivateKey,
    val publicKey: PublicKey,
    val privateKeyString: String,
    val publicKeyString: String
)