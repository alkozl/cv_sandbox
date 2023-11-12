package com.example.customviewsampleapp.di.module

import com.example.customviewsampleapp.utils.mapper.graph.GraphDomainToUiMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphDomainToUiMapperImpl
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphDtoToDomainMapperImpl
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDomainToUiMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDomainToUiMapperImpl
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDtoToDomainMapper
import com.example.customviewsampleapp.utils.mapper.graph.GraphPointDtoToDomainMapperImpl

class MapperModule : Module{
    fun provideGraphPointDtoToDomainMapper(): GraphPointDtoToDomainMapper {
        return GraphPointDtoToDomainMapperImpl()
    }

    fun provideGraphDtoToDomainMapper(graphPointDtoToDomainMapper: GraphPointDtoToDomainMapper): GraphDtoToDomainMapper {
        return GraphDtoToDomainMapperImpl(graphPointDtoToDomainMapper)
    }
    
    fun provideGraphPointDomainToUiMapper(): GraphPointDomainToUiMapper {
        return GraphPointDomainToUiMapperImpl()
    }

    fun provideGraphDomainToUiMapper(graphPointDomainToUiMapper: GraphPointDomainToUiMapper): GraphDomainToUiMapper {
        return GraphDomainToUiMapperImpl(graphPointDomainToUiMapper)
    }
}