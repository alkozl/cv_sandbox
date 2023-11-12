package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.utils.coroutine.doOnDefault

class GraphDtoToDomainMapperImpl(
    private val graphPointDtoToDomainMapper: GraphPointDtoToDomainMapper
) : GraphDtoToDomainMapper {
    override suspend fun convert(input: GraphDto): Graph {
        return input.run {
            val pointsDomain =
                doOnDefault { points.map { graphPointDtoToDomainMapper.convert(it) } }
            Graph(points = pointsDomain)
        }
    }
}