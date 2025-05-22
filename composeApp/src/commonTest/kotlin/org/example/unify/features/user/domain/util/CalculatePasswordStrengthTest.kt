package org.example.unify.features.user.domain.util

import org.example.unify.features.user.domain.entity.PasswordStrength
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePasswordStrengthTest {

    // Test cases for STRONG password
    @Test
    fun `STRONG - length 8 has special char has uppercase`() {
        assertEquals(PasswordStrength.STRONG, calculatePasswordStrength("Str0ng!P"))
    }

    @Test
    fun `STRONG - length more than 8 has special char has uppercase`() {
        assertEquals(PasswordStrength.STRONG, calculatePasswordStrength("EvenStr0nger!Pass1"))
    }

    @Test
    fun `STRONG - minimum requirements met precisely`() {
        assertEquals(PasswordStrength.STRONG, calculatePasswordStrength("Abcdef1!"))
    }

    @Test
    fun `STRONG - multiple special chars and uppercases`() {
        assertEquals(PasswordStrength.STRONG, calculatePasswordStrength("!@#\$%^&*ABCDEFG123"))
    }

    // Test cases for MEDIUM password
    @Test
    fun `MEDIUM - length 8 has special char no uppercase`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("medium!p"))
    }

    @Test
    fun `MEDIUM - length 8 no special char has uppercase`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("MediumP1"))
    }

    @Test
    fun `MEDIUM - length more than 8 has special char no uppercase`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("longermedium!pass1"))
    }

    @Test
    fun `MEDIUM - length more than 8 no special char has uppercase`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("LongerMediumPass1"))
    }

    @Test
    fun `MEDIUM - meets length only special char`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("abcdefg!"))
    }

    @Test
    fun `MEDIUM - meets length only uppercase`() {
        assertEquals(PasswordStrength.MEDIUM, calculatePasswordStrength("Abcdefgh"))
    }


    // Test cases for WEAK password
    @Test
    fun `WEAK - length less than 8 has special char has uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("Sh0rt!P"))
    }

    @Test
    fun `WEAK - length 8 no special char no uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("weakpass"))
    }

    @Test
    fun `WEAK - length less than 8 no special char no uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("weak"))
    }

    @Test
    fun `WEAK - length less than 8 has special char no uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("sh0rt!"))
    }

    @Test
    fun `WEAK - length less than 8 no special char has uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("Sh0rtP"))
    }

    @Test
    fun `WEAK - empty password`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength(""))
    }

    @Test
    fun `WEAK - only special chars length less than 8`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("!@#\$%^"))
    }

    @Test
    fun `WEAK - only uppercases length less than 8`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("ABCDEFG"))
    }

    @Test
    fun `WEAK - only numbers length 8 or more`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("1234567890"))
    }

    @Test
    fun `WEAK - only lowercase length 8 or more`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("abcdefghijkl"))
    }

    @Test
    fun `WEAK - length 7 has special and uppercase`() {
        assertEquals(PasswordStrength.WEAK, calculatePasswordStrength("Abcde1!"))
    }
}