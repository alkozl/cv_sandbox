package com.example.customviewsampleapp.data.api.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult

interface GraphApi {
    suspend fun getGraph(): OperationResult<GraphDto>
}

