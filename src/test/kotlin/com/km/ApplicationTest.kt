package com.km

import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testCrud() = testApplication {
        application {
            module()
        }
        client.post("/api/counters") {
            this.parameter("name", "test")
            this.parameter("value", 1L)

        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }

        client.patch("/api/counters/increment") {
            this.parameter("name", "test")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(2L, bodyAsText().toLong())
        }
    }
}
