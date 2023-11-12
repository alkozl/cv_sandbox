package com.example.customviewsampleapp.domain.graph

import com.example.customviewsampleapp.domain.repository.graph.GraphRepository
import com.example.customviewsampleapp.model.ui.GraphUi
import com.example.customviewsampleapp.utils.mapper.graph.GraphDomainToUiMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper
import com.example.customviewsampleapp.utils.operation_result.OperationResult
import com.example.customviewsampleapp.utils.operation_result.asSuccessResult

class GetGraphUseCaseImpl(
    private val graphRepository: GraphRepository,
    private val graphDtoToDomainMapper: GraphDtoToDomainMapper,
    private val graphDomainToUiMapper: GraphDomainToUiMapper
) : GetGraphUseCase {
    override suspend fun invoke(): OperationResult<GraphUi> {
        return graphRepository.getGraph().flatMapIfSuccessSuspend { graphDto ->
            val graphDomain = graphDtoToDomainMapper.convert(graphDto)
            val graphUi = graphDomainToUiMapper.convert(graphDomain)
            graphUi.asSuccessResult()
        }
    }
}