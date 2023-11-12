package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.domain.GraphPoint
import com.example.customviewsampleapp.model.ui.GraphPointUi
import com.example.customviewsampleapp.utils.mapper.Mapper

interface GraphPointDomainToUiMapper : Mapper<GraphPoint, GraphPointUi> {
    override suspend fun convert(input: GraphPoint): GraphPointUi {
        return input.run {
            GraphPointUi(
                id = id,
                date = date,
                value = value
            )
        }
    }
}