package org.example.unify.features.user.domain.util

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlin.test.Test

class IsValidEmailTest {
    @Test
    fun `valid email with standard format returns true`() {
        assertThat(isValidEmail("test@example.com")).isTrue()
    }

    @Test
    fun `valid email with subdomain returns true`() {
        assertThat(isValidEmail("test@subdomain.example.com")).isTrue()
    }

    @Test
    fun `valid email with hyphen in local part returns true`() {
        assertThat(isValidEmail("test-user@example.com")).isTrue()
    }

    @Test
    fun `valid email with dot in local part returns true`() {
        assertThat(isValidEmail("test.user@example.com")).isTrue()
    }

    @Test
    fun `valid email with underscore in local part returns true`() {
        assertThat(isValidEmail("test_user@example.com")).isTrue()
    }

    @Test
    fun `valid email with numbers in local part returns true`() {
        assertThat(isValidEmail("test123user@example.com")).isTrue()
    }

    @Test
    fun `valid email with hyphen in domain returns true`() {
        assertThat(isValidEmail("test@example-domain.com")).isTrue()
    }

    @Test
    fun `valid email with numbers in domain returns true`() {
        assertThat(isValidEmail("test@example123.com")).isTrue()
    }

    @Test
    fun `valid email with multiple domain parts returns true`() {
        assertThat(isValidEmail("test@example.co.uk")).isTrue()
    }

    @Test
    fun `valid email with single character local part returns true`() {
        assertThat(isValidEmail("a@example.com")).isTrue()
    }

    @Test
    fun `valid email with single character domain name returns true`() {
        assertThat(isValidEmail("test@e.com")).isTrue()
    }

    // Invalid email formats

    @Test
    fun `empty string input returns false`() {
        assertThat(isValidEmail("")).isFalse()
    }

    @Test
    fun `missing at symbol returns false`() {
        assertThat(isValidEmail("testexample.com")).isFalse()
    }

    @Test
    fun `missing local part returns false`() {
        assertThat(isValidEmail("@example.com")).isFalse()
    }

    @Test
    fun `missing domain part returns false`() {
        assertThat(isValidEmail("test@")).isFalse()
    }

    @Test
    fun `missing top-level domain returns false`() {
        assertThat(isValidEmail("test@example")).isFalse()
    }

    @Test
    fun `domain part starts with hyphen returns false`() {
        assertThat(isValidEmail("test@-example.com")).isFalse()
    }

    @Test
    fun `consecutive dots in domain part returns false`() {
        assertThat(isValidEmail("test@example..com")).isFalse()
    }

    @Test
    fun `email with leading space returns false`() {
        assertThat(isValidEmail(" test@example.com")).isFalse()
    }

    @Test
    fun `email with trailing space returns false`() {
        assertThat(isValidEmail("test@example.com ")).isFalse()
    }

    @Test
    fun `email with space in local part returns false`() {
        assertThat(isValidEmail("test user@example.com")).isFalse()
    }

    @Test
    fun `email with space in domain part returns false`() {
        assertThat(isValidEmail("test@exam ple.com")).isFalse()
    }


    @Test
    fun `TLD missing after dot returns false`() {
        assertThat(isValidEmail("test@example.")).isFalse()
    }

    // Edge cases (depending on your specific email validation regex/logic)

    @Test
    fun `unicode characters in local part returns false`() {
        assertThat(isValidEmail("tést@example.com")).isFalse()
    }

    @Test
    fun `unicode characters in domain part returns false`() {
        assertThat(isValidEmail("test@éxample.com")).isFalse()
    }

    @Test
    fun `local part too long returns false`() {
        val longLocalPart = "a".repeat(260)
        assertThat(isValidEmail("$longLocalPart@example.com")).isFalse()
    }

    @Test
    fun `domain part too long returns false`() {
        val longDomainPart = "a".repeat(260)
        assertThat(isValidEmail("test@$longDomainPart.com")).isFalse()
    }

    @Test
    fun `overall email too long returns false`() {
        val veryLongEmail =
            "a".repeat(100) + "@" + "b".repeat(100) + "." + "c".repeat(50)
        assertThat(isValidEmail(veryLongEmail)).isFalse()
    }
}