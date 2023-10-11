package com.example.customviewsampleapp.data.rds.graph

import com.example.customviewsampleapp.data.rds.RemoteDataSource
import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.utils.operation_result.OperationResult

interface GraphRds : RemoteDataSource {
    suspend fun getGraph(): OperationResult<GraphDto>
}