package com.prewave.tree.service

import com.prewave.tree.TreeServiceAbstractTest
import com.prewave.tree.exception.AlreadyExistsException
import com.prewave.tree.exception.NotFoundException
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class EdgeServiceTest : TreeServiceAbstractTest() {
    @Test
    fun `create node - success`() {
        val result = edgeService.create(EDGE_12)
        assertEquals(EDGE_12, result)
    }

    @Test
    fun `create node - fail (already exists)`() {
        edgeService.create(EDGE_12)
        assertThrows<AlreadyExistsException> { edgeService.create(EDGE_12) }
    }

    @Test
    fun `delete node - fail (non existing node)`() {
        assertThrows<NotFoundException> { edgeService.delete(EDGE_12) }
    }

    @Test
    fun `delete - success`() {
        edgeService.create(EDGE_12)
        val result = edgeService.delete(EDGE_12)
        assertEquals(1, result)
    }

    @Test
    fun `delete - success (cascade)`() {
        edgeService.create(EDGE_12)
        edgeService.create(EDGE_24)
        val result = edgeService.delete(EDGE_12)
        assertEquals(2, result)
    }
}
