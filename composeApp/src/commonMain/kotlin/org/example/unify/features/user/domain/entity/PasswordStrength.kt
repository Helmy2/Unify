package org.example.unify.features.user.domain.entity

enum class PasswordStrength(val strengthValue: Float) {
    WEAK(0.33f),
    MEDIUM(0.66f),
    STRONG(1f)
}