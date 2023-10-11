package com.example.customviewsampleapp.domain.graph

import com.example.customviewsampleapp.domain.repository.graph.GraphRepository
import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper
import com.example.customviewsampleapp.utils.operation_result.OperationResult
import com.example.customviewsampleapp.utils.operation_result.asSuccessResult

class GetGraphUseCaseImpl(
    private val graphRepository: GraphRepository,
    private val graphDtoToDomainMapper: GraphDtoToDomainMapper
) : GetGraphUseCase {
    override suspend fun invoke(): OperationResult<Graph> {
        return graphRepository.getGraph().flatMapIfSuccess {
            graphDtoToDomainMapper.convert(it).asSuccessResult()
        }
    }
}