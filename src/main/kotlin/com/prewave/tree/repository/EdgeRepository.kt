package com.prewave.tree.repository

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.exception.AlreadyExistsException
import com.prewave.tree.exception.InvalidTreeException
import com.prewave.tree.exception.NotFoundException
import com.prewave.tree.jooq.tables.records.EdgeRecord
import com.prewave.tree.jooq.tables.references.EDGE
import com.prewave.tree.repository.RepositoryUtils.isSubtree
import org.jooq.DSLContext
import org.jooq.postgres.extensions.types.Ltree
import org.springframework.stereotype.Repository

@Repository
class EdgeRepository(
    private val dsl: DSLContext,
) {
    // Create edge and calculate ltree path
    fun create(edgeDto: EdgeDto): Boolean {
        if (get(edgeDto) != null) {
            throw AlreadyExistsException(edgeDto)
        }
        val parentPath = getParentPath(edgeDto.fromId)
        return dsl
            .insertInto(EDGE)
            .columns(EDGE.FROM_ID, EDGE.TO_ID, EDGE.PATH)
            .values(
                edgeDto.fromId,
                edgeDto.toId,
                Ltree.valueOf("$parentPath.${edgeDto.toId}"),
            ).execute() > 0
    }

    // Delete edge with all children edges
    fun delete(edgeDto: EdgeDto): Int {
        val path = get(edgeDto)?.path ?: throw NotFoundException(edgeDto)
        return dsl
            .delete(EDGE)
            .where(EDGE.PATH.name.isSubtree(path))
            .execute()
    }

    fun get(edgeDto: EdgeDto): EdgeRecord? =
        dsl
            .selectFrom(EDGE)
            .where(EDGE.FROM_ID.eq(edgeDto.fromId).and(EDGE.TO_ID.eq(edgeDto.toId)))
            .fetchOne()

    fun getParentPath(id: Int): Ltree = getByTo(id)?.path ?: Ltree.valueOf(id.toString())

    private fun getByTo(id: Int): EdgeRecord? {
        val result =
            dsl
                .selectFrom(EDGE)
                .where(EDGE.TO_ID.eq(id))
                .fetch()
        return if (result.size > 1) {
            throw InvalidTreeException(id)
        } else {
            result.firstOrNull()
        }
    }
}
