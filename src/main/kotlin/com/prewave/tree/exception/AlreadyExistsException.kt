package com.prewave.tree.exception

import com.prewave.tree.dto.EdgeDto

class AlreadyExistsException(
    edgeDto: EdgeDto,
) : Exception("Edge (${edgeDto.fromId}, ${edgeDto.toId}) already exists")
