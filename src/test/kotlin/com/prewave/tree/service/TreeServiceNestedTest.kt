package com.prewave.tree.service

import com.prewave.tree.TreeServiceAbstractTest
import com.prewave.tree.dto.NodeDto
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test
import kotlin.test.assertEquals

class TreeServiceNestedTest : TreeServiceAbstractTest() {
    @Autowired
    lateinit var treeService: TreeService

    companion object {
        val NESTED_TREE_0 =
            NodeDto(
                EDGE_01.fromId,
                mutableListOf(
                    NodeDto(
                        EDGE_01.toId,
                        mutableListOf(),
                    ),
                ),
            )
        val NESTED_TREE_1 =
            NodeDto(
                EDGE_12.fromId,
                mutableListOf(
                    NodeDto(
                        EDGE_12.toId,
                        mutableListOf(),
                    ),
                    NodeDto(
                        EDGE_13.toId,
                        mutableListOf(),
                    ),
                ),
            )
        val NESTED_TREE_2 =
            NodeDto(
                EDGE_25.fromId,
                mutableListOf(
                    NodeDto(
                        EDGE_24.toId,
                        mutableListOf(),
                    ),
                    NodeDto(
                        EDGE_25.toId,
                        mutableListOf(
                            NodeDto(
                                EDGE_56.toId,
                                mutableListOf(),
                            ),
                        ),
                    ),
                ),
            )
    }

    @Test
    fun `get tree node 0 - nested`() {
        createTree()
        val result = treeService.getTreeNested(0, 1)
        assertEquals(NESTED_TREE_0, result)
    }

    @Test
    fun `get tree node 1 - nested`() {
        createTree()
        val result = treeService.getTreeNested(1, 1)
        assertEquals(NESTED_TREE_1, result)
    }

    @Test
    fun `get tree node 2 - nested`() {
        createTree()
        val result = treeService.getTreeNested(2, 2)
        assertEquals(NESTED_TREE_2, result)
    }

    @Test
    fun `get tree node 1 - too big depth`() {
        createTree()
        assertThrows<IllegalArgumentException> { treeService.getTreeNested(1, 11) }
    }
}
