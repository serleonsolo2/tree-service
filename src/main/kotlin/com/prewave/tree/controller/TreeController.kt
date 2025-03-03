package com.prewave.tree.controller

import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.dto.NodeDto
import com.prewave.tree.service.TreeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/tree")
class TreeController(
    private val treeService: TreeService,
) {
    companion object {
        private const val DEFAULT_DEPTH = 3
    }

    @GetMapping("/{rootId}")
    @Operation(summary = "Get tree for node in flattened json format")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "409", description = "Invalid tree detected"),
        ],
    )
    fun getTree(
        @PathVariable rootId: Int,
    ): ResponseEntity<List<EdgeDto>> =
        ResponseEntity
            .ok()
            .body(treeService.getTree(rootId))

    @GetMapping("/{rootId}/nested")
    @Operation(summary = "Get tree for node in nested json format and limited depth")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "400", description = "Depth exceed the value configured in the service"),
            ApiResponse(responseCode = "409", description = "Invalid tree detected"),
        ],
    )
    fun getTreeNested(
        @PathVariable rootId: Int,
        @RequestParam(required = false) maxDepth: Int = DEFAULT_DEPTH,
    ): ResponseEntity<NodeDto> =
        ResponseEntity
            .ok()
            .body(
                treeService.getTreeNested(
                    rootId,
                    maxDepth,
                ),
            )
}
