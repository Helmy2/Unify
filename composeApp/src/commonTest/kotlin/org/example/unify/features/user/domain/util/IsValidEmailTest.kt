package org.example.unify.features.user.domain.util

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsValidEmailTest {
    @Test
    fun `valid email with standard format returns true`() {
        assertTrue(isValidEmail("test@example.com"))
    }

    @Test
    fun `valid email with subdomain returns true`() {
        assertTrue(isValidEmail("test@subdomain.example.com"))
    }

    @Test
    fun `valid email with hyphen in local part returns true`() {
        assertTrue(isValidEmail("test-user@example.com"))
    }

    @Test
    fun `valid email with dot in local part returns true`() {
        assertTrue(isValidEmail("test.user@example.com"))
    }

    @Test
    fun `valid email with underscore in local part returns true`() {
        assertTrue(isValidEmail("test_user@example.com"))
    }

    @Test
    fun `valid email with numbers in local part returns true`() {
        assertTrue(isValidEmail("test123user@example.com"))
    }

    @Test
    fun `valid email with hyphen in domain returns true`() {
        assertTrue(isValidEmail("test@example-domain.com"))
    }

    @Test
    fun `valid email with numbers in domain returns true`() {
        assertTrue(isValidEmail("test@example123.com"))
    }

    @Test
    fun `valid email with multiple domain parts returns true`() {
        assertTrue(isValidEmail("test@example.co.uk"))
    }

    @Test
    fun `valid email with single character local part returns true`() {
        assertTrue(isValidEmail("a@example.com"))
    }

    @Test
    fun `valid email with single character domain name returns true`() {
        assertTrue(isValidEmail("test@e.com"))
    }

    // Invalid email formats

    @Test
    fun `empty string input returns false`() {
        assertFalse(isValidEmail(""))
    }

    @Test
    fun `missing at symbol returns false`() {
        assertFalse(isValidEmail("testexample.com"))
    }

    @Test
    fun `missing local part returns false`() {
        assertFalse(isValidEmail("@example.com"))
    }

    @Test
    fun `missing domain part returns false`() {
        assertFalse(isValidEmail("test@"))
    }

    @Test
    fun `missing top-level domain returns false`() {
        assertFalse(isValidEmail("test@example"))
    }

    @Test
    fun `domain part starts with hyphen returns false`() {
        assertFalse(isValidEmail("test@-example.com"))
    }

    @Test
    fun `consecutive dots in domain part returns false`() {
        assertFalse(isValidEmail("test@example..com"))
    }

    @Test
    fun `email with leading space returns false`() {
        assertFalse(isValidEmail(" test@example.com"))
    }

    @Test
    fun `email with trailing space returns false`() {
        assertFalse(isValidEmail("test@example.com "))
    }

    @Test
    fun `email with space in local part returns false`() {
        assertFalse(isValidEmail("test user@example.com"))
    }

    @Test
    fun `email with space in domain part returns false`() {
        assertFalse(isValidEmail("test@exam ple.com"))
    }

    @Test
    fun `TLD missing after dot returns false`() {
        assertFalse(isValidEmail("test@example."))
    }

    // Edge cases (depending on your specific email validation regex/logic)

    @Test
    fun `unicode characters in local part returns false`() {
        assertFalse(isValidEmail("tést@example.com"))
    }

    @Test
    fun `unicode characters in domain part returns false`() {
        assertFalse(isValidEmail("test@éxample.com"))
    }

    @Test
    fun `local part too long returns false`() {
        val longLocalPart = "a".repeat(260)
        assertFalse(isValidEmail("$longLocalPart@example.com"))
    }

    @Test
    fun `domain part too long returns false`() {
        val longDomainPart = "a".repeat(260)
        assertFalse(isValidEmail("test@$longDomainPart.com"))
    }

    @Test
    fun `overall email too long returns false`() {
        val veryLongEmail =
            "a".repeat(100) + "@" + "b".repeat(100) + "." + "c".repeat(50)
        assertFalse(isValidEmail(veryLongEmail))
    }
}