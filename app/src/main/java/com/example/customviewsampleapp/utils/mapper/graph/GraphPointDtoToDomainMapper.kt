package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.data.graph.GraphPointDto
import com.example.customviewsampleapp.model.domain.GraphPoint
import com.example.customviewsampleapp.utils.mapper.Mapper

interface GraphPointDtoToDomainMapper : Mapper<GraphPointDto, GraphPoint> {
    override fun convert(input: GraphPointDto): GraphPoint {
        return input.run {
            GraphPoint(
                id = id,
                date = date,
                value = value
            )
        }
    }
}