package com.prewave.tree.service

import com.prewave.tree.TreeServiceAbstractTest
import kotlin.test.Test
import kotlin.test.assertEquals
import org.springframework.beans.factory.annotation.Autowired

class TreeServiceFlattenedTest : TreeServiceAbstractTest() {
    @Autowired
    lateinit var treeService: TreeService

    companion object {
        val FLAT_TREE_1 =
            listOf(
                EDGE_12,
                EDGE_13,
                EDGE_24,
                EDGE_25,
                EDGE_56,
                EDGE_37,
            )
        val FLAT_TREE_2 =
            listOf(
                EDGE_24,
                EDGE_25,
                EDGE_56,
            )
    }

    @Test
    fun `get tree node 1 - flattened`() {
        createTree()
        val result = treeService.getTree(1)
        assertEquals(FLAT_TREE_1, result)
    }

    @Test
    fun `get tree node 2 - flattened`() {
        createTree()
        val result = treeService.getTree(2)
        assertEquals(FLAT_TREE_2, result)
    }
}

