package com.example.customviewsampleapp.data.api.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult
import com.example.customviewsampleapp.utils.operation_result.Success

class GraphApiImpl : GraphApi {
    override suspend fun getGraph(): OperationResult<GraphDto> {
        return Success(GraphDto(emptyList()))
    }
}