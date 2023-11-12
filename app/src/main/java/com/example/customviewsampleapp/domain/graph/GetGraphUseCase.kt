package com.example.customviewsampleapp.domain.graph

import com.example.customviewsampleapp.domain.UseCase
import com.example.customviewsampleapp.model.ui.GraphUi
import com.example.customviewsampleapp.utils.operation_result.OperationResult

interface GetGraphUseCase : UseCase {
    suspend operator fun invoke(): OperationResult<GraphUi>
}
