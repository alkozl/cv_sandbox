package com.example.customviewsampleapp.data.rds.graph

import com.example.customviewsampleapp.data.api.graph.GraphApi
import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult

class GraphRdsImpl(
    private val graphApi: GraphApi
) : GraphRds {
    override suspend fun getGraph(): OperationResult<GraphDto> {
        return graphApi.getGraph()
    }
}