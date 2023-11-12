package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.utils.mapper.Mapper

interface GraphDtoToDomainMapper : Mapper<GraphDto, Graph> {
    override suspend fun convert(input: GraphDto): Graph
}