package com.example.customviewsampleapp.di.module

import com.example.customviewsampleapp.domain.graph.GetGraphUseCase
import com.example.customviewsampleapp.domain.graph.GetGraphUseCaseImpl
import com.example.customviewsampleapp.domain.repository.graph.GraphRepository
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper

class UseCaseModule : Module {
    fun provideGetGraphUseCase(
        graphRepository: GraphRepository,
        graphDtoToDomainMapper: GraphDtoToDomainMapper
    ): GetGraphUseCase {
        return GetGraphUseCaseImpl(
            graphRepository = graphRepository,
            graphDtoToDomainMapper = graphDtoToDomainMapper
        )
    }
}