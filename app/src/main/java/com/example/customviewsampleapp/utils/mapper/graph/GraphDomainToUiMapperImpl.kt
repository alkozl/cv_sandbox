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
            val maxValue = doOnDefault { pointsUi.maxBy { it.value } }
            val minValue = doOnDefault { pointsUi.minBy { it.value } }
            GraphUi(points = pointsUi, maxValue = maxValue, minValue = minValue)
        }
    }
}