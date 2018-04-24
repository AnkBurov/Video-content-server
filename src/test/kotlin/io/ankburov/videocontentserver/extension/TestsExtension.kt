package ru.rgs.k6.extension

import org.junit.Assert.assertEquals
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> ResponseEntity<T?>.ok(message: String? = null): ResponseEntity<T?> {
    assertEquals(message, HttpStatus.OK, this.statusCode)
    return this
}

fun <T> ResponseEntity<T?>.unprocessableEntity(message: String? = null): ResponseEntity<T?> {
    assertEquals(message, HttpStatus.UNPROCESSABLE_ENTITY, this.statusCode)
    return this
}

fun <T> ResponseEntity<T?>.badRequest(message: String? = null): ResponseEntity<T?> {
    assertEquals(message, HttpStatus.BAD_REQUEST, this.statusCode)
    return this
}

fun <T> ResponseEntity<T?>.status(httpStatus: HttpStatus, message: String? = null): ResponseEntity<T?> {
    assertEquals(message, httpStatus, this.statusCode)
    return this
}

fun <T> ResponseEntity<T?>.isMp4ContentType(): ResponseEntity<T?> {
    headers[CONTENT_TYPE]!! equalsTo listOf("video/mp4")
    return this
}

fun <T> ResponseEntity<T?>.isTextPlain(): ResponseEntity<T?> {
    headers[CONTENT_TYPE]!! equalsTo listOf("text/plain")
    return this
}

fun <T> ResponseEntity<T?>.bodyNotNull(message: String? = null) = this.body ?: throw AssertionError(message)

fun String.trimIfNeeded(trim: Boolean) = if (trim) this.trim() else this

inline fun <reified R> TestRestTemplate.send(url: String,
                                             method: HttpMethod,
                                             requestEntity: HttpEntity<Any>? = null): ResponseEntity<R?> {
    return this.exchange(url, method, requestEntity, object : ParameterizedTypeReference<R>() {})
}

infix fun <EXPECTED, ACTUAL> ACTUAL.equalsTo(arg: EXPECTED) {
    assertEquals(arg, this)
}

fun ByteArray.asString() = String(this)