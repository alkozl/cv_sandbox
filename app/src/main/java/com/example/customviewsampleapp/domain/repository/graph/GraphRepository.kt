package com.example.customviewsampleapp.domain.repository.graph

import com.example.customviewsampleapp.data.repository.Repository
import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult

interface GraphRepository : Repository {
    suspend fun getGraph(): OperationResult<GraphDto>
}