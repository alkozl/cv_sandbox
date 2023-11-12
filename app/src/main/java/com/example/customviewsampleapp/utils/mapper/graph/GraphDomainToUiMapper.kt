package com.example.customviewsampleapp.utils.mapper.graph

import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.model.ui.GraphUi
import com.example.customviewsampleapp.utils.mapper.Mapper

interface GraphDomainToUiMapper : Mapper<Graph, GraphUi> {
    override suspend fun convert(input: Graph): GraphUi
}