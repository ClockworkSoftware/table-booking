package org.clockwork.tablebooking.config

import com.auth0.jwt.interfaces.RSAKeyProvider
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource
import java.security.KeyStore
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@ConfigurationProperties("app.security.jwt")
data class JwtConfig(
    val keyStoreResource: Resource,
    val keyStorePassword: String,
    val keyAlias: String,
    val keyPassword: String
) : RSAKeyProvider {

    private lateinit var publicKey: RSAPublicKey
    private lateinit var privateKey: RSAPrivateKey

    @PostConstruct
    fun init() {
        val keystore = KeyStore.getInstance("PKCS12")
        keyStoreResource.inputStream.use { keystore.load(it, keyStorePassword.toCharArray()) }
        publicKey = keystore.getCertificate(keyAlias).publicKey as RSAPublicKey
        privateKey = keystore.getKey(keyAlias, keyPassword.toCharArray()) as RSAPrivateKey
    }

    override fun getPublicKeyById(keyId: String?): RSAPublicKey? {
        return publicKey
    }

    override fun getPrivateKey(): RSAPrivateKey? {
        return privateKey
    }

    override fun getPrivateKeyId(): String? {
        return null
    }
}