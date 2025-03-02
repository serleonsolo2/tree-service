package com.prewave.tree.exception

import com.prewave.tree.dto.EdgeDto

class NotFoundException(
    edgeDto: EdgeDto,
) : Exception("Edge (${edgeDto.fromId}, ${edgeDto.toId}) not found")
