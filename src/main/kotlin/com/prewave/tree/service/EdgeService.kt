package com.prewave.tree.service

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.repository.EdgeRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EdgeService(
    val edgeRepository: EdgeRepository,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(EdgeService::class.java)
    }

    fun create(edgeDto: EdgeDto): EdgeDto {
        logger.info("Create new edge: $edgeDto")
        edgeRepository.create(edgeDto)
        return edgeDto
    }

    fun delete(edgeDto: EdgeDto): Int {
        logger.info("Delete edge: $edgeDto")
        return edgeRepository.delete(edgeDto)
    }
}
