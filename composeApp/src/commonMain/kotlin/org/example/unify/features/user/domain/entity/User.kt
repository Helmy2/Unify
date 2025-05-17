package org.example.unify.features.user.domain.entity

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import org.example.unify.core.util.SupabaseConfig


data class User(
    val id: String,
    val name: String,
    val email: String,
)

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get(SupabaseConfig.DISPLAY_NAME_KEY)?.jsonPrimitive?.content
        ?: SupabaseConfig.DEFAULT_NAME,
    email = this.email ?: "",
)