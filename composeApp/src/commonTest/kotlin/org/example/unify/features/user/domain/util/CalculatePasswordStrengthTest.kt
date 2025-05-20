package org.example.unify.features.user.domain.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.example.unify.features.user.domain.entity.PasswordStrength
import kotlin.test.Test

class CalculatePasswordStrengthTest {

    // Test cases for STRONG password
    @Test
    fun `STRONG - length 8 has special char has uppercase`() {
        assertThat(calculatePasswordStrength("Str0ng!P")).isEqualTo(PasswordStrength.STRONG)
    }

    @Test
    fun `STRONG - length more than 8 has special char has uppercase`() {
        assertThat(calculatePasswordStrength("EvenStr0nger!Pass1")).isEqualTo(PasswordStrength.STRONG)
    }

    @Test
    fun `STRONG - minimum requirements met precisely`() {
        assertThat(calculatePasswordStrength("Abcdef1!")).isEqualTo(PasswordStrength.STRONG)
    }

    @Test
    fun `STRONG - multiple special chars and uppercases`() {
        assertThat(calculatePasswordStrength("!@#\$%^&*ABCDEFG123")).isEqualTo(PasswordStrength.STRONG)
    }

    // Test cases for MEDIUM password
    @Test
    fun `MEDIUM - length 8 has special char no uppercase`() {
        assertThat(calculatePasswordStrength("medium!p")).isEqualTo(PasswordStrength.MEDIUM)
    }

    @Test
    fun `MEDIUM - length 8 no special char has uppercase`() {
        assertThat(calculatePasswordStrength("MediumP1")).isEqualTo(PasswordStrength.MEDIUM)
    }

    @Test
    fun `MEDIUM - length more than 8 has special char no uppercase`() {
        assertThat(calculatePasswordStrength("longermedium!pass1")).isEqualTo(PasswordStrength.MEDIUM)
    }

    @Test
    fun `MEDIUM - length more than 8 no special char has uppercase`() {
        assertThat(calculatePasswordStrength("LongerMediumPass1")).isEqualTo(PasswordStrength.MEDIUM)
    }

    @Test
    fun `MEDIUM - meets length only special char`() {
        assertThat(calculatePasswordStrength("abcdefg!")).isEqualTo(PasswordStrength.MEDIUM)
    }

    @Test
    fun `MEDIUM - meets length only uppercase`() {
        assertThat(calculatePasswordStrength("Abcdefgh")).isEqualTo(PasswordStrength.MEDIUM)
    }


    // Test cases for WEAK password
    @Test
    fun `WEAK - length less than 8 has special char has uppercase`() {
        assertThat(calculatePasswordStrength("Sh0rt!P")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - length 8 no special char no uppercase`() {
        assertThat(calculatePasswordStrength("weakpass")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - length less than 8 no special char no uppercase`() {
        assertThat(calculatePasswordStrength("weak")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - length less than 8 has special char no uppercase`() {
        assertThat(calculatePasswordStrength("sh0rt!")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - length less than 8 no special char has uppercase`() {
        assertThat(calculatePasswordStrength("Sh0rtP")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - empty password`() {
        assertThat(calculatePasswordStrength("")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - only special chars length less than 8`() {
        assertThat(calculatePasswordStrength("!@#\$%^")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - only uppercases length less than 8`() {
        assertThat(calculatePasswordStrength("ABCDEFG")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - only numbers length 8 or more`() {
        assertThat(calculatePasswordStrength("1234567890")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - only lowercase length 8 or more`() {
        assertThat(calculatePasswordStrength("abcdefghijkl")).isEqualTo(PasswordStrength.WEAK)
    }

    @Test
    fun `WEAK - length 7 has special and uppercase`() {
        assertThat(calculatePasswordStrength("Abcde1!")).isEqualTo(PasswordStrength.WEAK)
    }
}