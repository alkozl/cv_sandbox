package com.example.customviewsampleapp.di.module

import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapperImpl
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDtoToDomainMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDtoToDomainMapperImpl

class MapperModule : Module{
    fun provideGraphPointDtoToDomainMapper(): GraphPointDtoToDomainMapper {
        return GraphPointDtoToDomainMapperImpl()
    }

    fun provideGraphDtoToDomainMapper(graphPointDtoToDomainMapper: GraphPointDtoToDomainMapper): GraphDtoToDomainMapper {
        return GraphDtoToDomainMapperImpl(graphPointDtoToDomainMapper)
    }
}