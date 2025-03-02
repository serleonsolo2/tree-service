package com.prewave.tree.controller

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.service.EdgeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/edge")
class EdgeController(
    private val edgeService: EdgeService,
) {
    @PostMapping("/{fromId}/{toId}")
    @Operation(summary = "Create edge with fromId and toId nodes")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "400", description = "Node already exists"),
        ],
    )
    fun create(
        @PathVariable fromId: Int,
        @PathVariable toId: Int,
    ): ResponseEntity<EdgeDto> =
        ResponseEntity
            .ok()
            .body(edgeService.create(EdgeDto(fromId, toId)))

    @DeleteMapping("/{fromId}/{toId}")
    @Operation(summary = "Delete edge with fromId and toId nodes")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "404", description = "Node already exists"),
        ],
    )
    fun delete(
        @PathVariable fromId: Int,
        @PathVariable toId: Int,
    ): ResponseEntity<Int> =
        ResponseEntity
            .ok()
            .body(edgeService.delete(EdgeDto(fromId, toId)))
}
