package org.example.unify.core.domain.exceptions

interface ExceptionMapper {
    fun map(throwable: Throwable): Throwable
}