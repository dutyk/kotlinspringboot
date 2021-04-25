package io.kang.zuul

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import com.netflix.hystrix.exception.HystrixTimeoutException
import org.springframework.http.HttpStatus
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@Component
class ApiFallbackProvider: FallbackProvider {
    override fun getRoute(): String {
        return "*"
    }

    override fun fallbackResponse(route: String?, cause: Throwable?): ClientHttpResponse {
        var message = ""
        if (cause is HystrixTimeoutException) {
            message = "Timeout"
        } else {
            message = "Service exception"
        }
        return fallbackResponse(message)
    }

    fun fallbackResponse(message: String): ClientHttpResponse {

        return object : ClientHttpResponse {
            @Throws(IOException::class)
            override fun getStatusCode(): HttpStatus {
                return HttpStatus.OK
            }

            @Throws(IOException::class)
            override fun getRawStatusCode(): Int {
                return 200
            }

            @Throws(IOException::class)
            override fun getStatusText(): String {
                return "OK"
            }

            override fun close() {

            }

            @Throws(IOException::class)
            override fun getBody(): InputStream {
                val bodyText = String.format("{\"code\": 999,\"message\": \"Service unavailable:%s\"}", message)
                return ByteArrayInputStream(bodyText.toByteArray())
            }

            override fun getHeaders(): HttpHeaders {
                val headers = HttpHeaders()
                headers.setContentType(MediaType.APPLICATION_JSON)
                return headers
            }
        }
    }
}