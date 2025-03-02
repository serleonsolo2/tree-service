package com.prewave.tree

import com.prewave.tree.config.EmbeddedPostgresConfiguration
import com.prewave.tree.dto.EdgeDto
import com.prewave.tree.service.EdgeService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [EmbeddedPostgresConfiguration::class])
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
abstract class TreeServiceAbstractTest {
    @Autowired
    lateinit var edgeService: EdgeService

    companion object {
        val EDGE_01 = EdgeDto(0, 1)
        val EDGE_12 = EdgeDto(1, 2)
        val EDGE_13 = EdgeDto(1, 3)
        val EDGE_24 = EdgeDto(2, 4)
        val EDGE_25 = EdgeDto(2, 5)
        val EDGE_56 = EdgeDto(5, 6)
        val EDGE_37 = EdgeDto(3, 7)
    }

    protected fun createTree() {
        edgeService.create(EDGE_12)
        edgeService.create(EDGE_13)
        edgeService.create(EDGE_24)
        edgeService.create(EDGE_25)
        edgeService.create(EDGE_56)
        edgeService.create(EDGE_37)
    }

    @BeforeEach
    fun `create root edge`() {
        edgeService.create(EDGE_01)
    }

    @AfterEach
    fun `delete root edge`() {
        edgeService.delete(EDGE_01)
    }
}
