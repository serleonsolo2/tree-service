package com.prewave.tree.service

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.dto.NodeDto
import com.prewave.tree.repository.TreeRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TreeService(
    val treeRepository: TreeRepository,
    @Value("\${tree.max-depth}")
    val globalMaxDepth: Int,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(TreeService::class.java)
    }

    suspend fun getTree(rootId: Int): List<EdgeDto> {
        logger.info("Get flat tree for the root: $rootId")
        return treeRepository
            .getTree(rootId)
            .map {
                EdgeDto(
                    fromId = it.fromId,
                    toId = it.toId,
                )
            }
    }

    fun getTreeNested(
        rootId: Int,
        maxDepth: Int,
    ): NodeDto {
        logger.info("Get nested tree for the root: $rootId and maxDepth: $maxDepth")
        require(maxDepth <= globalMaxDepth) { "Max depth can't be set more than $globalMaxDepth, decrease it or use other /tree endpoint" }
        val flatNodes = treeRepository.getTreeByLevel(rootId, maxDepth)
        return convertToNested(flatNodes, rootId)
    }

    // Convert flat list to the nested structure
    private fun convertToNested(
        flatNodes: List<EdgeDto>,
        rootId: Int,
    ): NodeDto {
        val map = flatNodes.associate { it.fromId to NodeDto(it.fromId, mutableListOf()) }
        flatNodes.forEach {
            val childNode = map[it.toId] ?: NodeDto(it.toId, mutableListOf())
            map[it.fromId]?.children?.add(childNode)
        }
        return map[rootId] ?: NodeDto(rootId, mutableListOf())
    }
}
