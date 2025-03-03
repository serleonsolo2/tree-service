package com.prewave.tree.repository

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.jooq.tables.records.EdgeRecord
import com.prewave.tree.jooq.tables.references.EDGE
import com.prewave.tree.repository.RepositoryUtils.intField
import com.prewave.tree.repository.RepositoryUtils.isSubtree
import org.jooq.CommonTableExpression
import org.jooq.DSLContext
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.inline
import org.jooq.impl.DSL.name
import org.jooq.impl.DSL.select
import org.springframework.stereotype.Repository

@Repository
class TreeRepository(
    private val dsl: DSLContext,
    private val edgeRepository: EdgeRepository,
) {
    companion object {
        private const val FROM_ID = "from_id"
        private const val TO_ID = "to_id"
        private const val TREE = "tree"
        private const val LEVEL = "level"
    }

    // get tree using ltree path
    fun getTree(rootId: Int): List<EdgeRecord> {
        val rootPath = edgeRepository.getParentPath(rootId)
        return dsl
            .selectFrom(EDGE)
            .where(
                EDGE.PATH.name
                    .isSubtree(rootPath)
                    .and(EDGE.PATH.ne(rootPath)),
            ).fetch()
    }

    // get tree using recursive cte with depth limitation
    fun getTreeByLevel(
        rootId: Int,
        maxDepth: Int,
    ): List<EdgeDto> {
        val cte = recursiveByLevel(rootId, maxDepth)
        return dsl
            .withRecursive(cte)
            .select(field(FROM_ID), field(TO_ID))
            .from(cte)
            .fetchInto(EdgeDto::class.java)
    }

    private fun recursiveByLevel(
        rootId: Int,
        maxDepth: Int,
    ): CommonTableExpression<*> =
        name(TREE)
            .fields(FROM_ID, TO_ID, LEVEL)
            .`as`(
                select(EDGE.FROM_ID, EDGE.TO_ID, inline(1).`as`(LEVEL))
                    .from(EDGE)
                    .where(EDGE.FROM_ID.eq(rootId))
                    .union(
                        select(EDGE.FROM_ID, EDGE.TO_ID, TREE.intField(LEVEL).plus(1))
                            .from(TREE)
                            .join(EDGE)
                            .on(EDGE.FROM_ID.eq(TREE.intField(TO_ID)))
                            .where(TREE.intField(LEVEL).lessThan(maxDepth)),
                    ),
            )
}
