package com.prewave.tree.exception

class InvalidTreeException(
    id: Int,
) : Exception("Invalid tree, check node $id")
