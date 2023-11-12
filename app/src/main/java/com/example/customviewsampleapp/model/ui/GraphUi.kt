package com.example.customviewsampleapp.model.ui

data class GraphUi(
    val points: List<GraphPointUi> = emptyList(),
    val maxValue: GraphPointUi = GraphPointUi(),
    val minValue: GraphPointUi = GraphPointUi()
)
