package com.km

import com.km.configuration.configureRouting
import com.km.configuration.configureSerialization
import com.km.dao.DatabaseFactory
import io.ktor.network.tls.certificates.buildKeyStore
import io.ktor.network.tls.certificates.saveToFile
import io.ktor.server.application.Application
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory
import java.io.File

fun main() {
    val keyStoreFile = File("build/keystore.jks")
    val keyStore = buildKeyStore {
        certificate("alias") {
            password = "private"
            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
        }
    }
    keyStore.saveToFile(keyStoreFile, "test")

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = "alias",
            keyStorePassword = { "test".toCharArray() },
            privateKeyPassword = { "private".toCharArray() }) {
            port = 88
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
    embeddedServer(Netty, environment).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
}
