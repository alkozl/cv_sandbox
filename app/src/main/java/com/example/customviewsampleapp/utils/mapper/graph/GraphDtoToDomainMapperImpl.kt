package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.model.domain.Graph

class GraphDtoToDomainMapperImpl(
    private val graphPointDtoToDomainMapper: GraphPointDtoToDomainMapper
) : GraphDtoToDomainMapper {
    override fun convert(input: GraphDto): Graph {
        return input.run {
            Graph(points = points.map(graphPointDtoToDomainMapper::convert))
        }
    }
}