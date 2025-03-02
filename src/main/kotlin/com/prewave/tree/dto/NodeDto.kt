package com.prewave.tree.dto

data class NodeDto(
    val nodeId: Int,
    val children: MutableList<NodeDto>,
)
