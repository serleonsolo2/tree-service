package com.prewave.tree.repository

import org.jooq.Condition
import org.jooq.impl.DSL.condition
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.inline
import org.jooq.impl.DSL.name
import org.jooq.postgres.extensions.types.Ltree

object RepositoryUtils {
    fun String.intField(fieldName: String) = field(name(this, fieldName), Int::class.java)

    // to avoid jooq warnings we convert ltree to string
    fun String.isSubtree(path: Ltree): Condition =
        condition(
            "{0} <@ {1}",
            field(this, String::class.java),
            inline(path, String::class.java),
        )
}
