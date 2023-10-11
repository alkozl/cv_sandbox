package com.example.customviewsampleapp.data.repository.graph

import com.example.customviewsampleapp.data.rds.graph.GraphRds
import com.example.customviewsampleapp.domain.repository.graph.GraphRepository
import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult

class GraphRepositoryImpl(
    private val graphRds: GraphRds
) : GraphRepository {
    override suspend fun getGraph(): OperationResult<GraphDto> {
        return graphRds.getGraph()
    }
}