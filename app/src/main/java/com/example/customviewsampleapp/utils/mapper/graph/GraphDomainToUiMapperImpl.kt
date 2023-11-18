package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.model.ui.GraphUi
import com.example.customviewsampleapp.utils.coroutine.doOnDefault

class GraphDomainToUiMapperImpl(
    private val graphPointDomainToUiMapper: GraphPointDomainToUiMapper
) : GraphDomainToUiMapper {
    override suspend fun convert(input: Graph): GraphUi {
        return input.run {
            val pointsUi = points.map { graphPointDomainToUiMapper.convert(it) }
            val (minValue, maxValue) = doOnDefault {
                val sortedPoints = pointsUi.sortedBy { it.value }
                sortedPoints.first() to sortedPoints.last()
            }
            GraphUi(points = pointsUi, maxValue = maxValue, minValue = minValue)
        }
    }
}