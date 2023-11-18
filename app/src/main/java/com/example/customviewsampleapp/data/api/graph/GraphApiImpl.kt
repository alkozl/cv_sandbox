package com.example.customviewsampleapp.data.api.graph

import com.example.customviewsampleapp.model.data.graph.GraphDto
import com.example.customviewsampleapp.model.data.graph.GraphPointDto
import com.example.customviewsampleapp.utils.constants.DateConstants
import com.example.customviewsampleapp.utils.operation_result.OperationResult
import com.example.customviewsampleapp.utils.operation_result.Success
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class GraphApiImpl : GraphApi {
    // todo Добавить сохранение графика
    override suspend fun getGraph(): OperationResult<GraphDto> {
        val pointsCountRandom = Random.nextInt(5, 25)

        val now = LocalDateTime.now()
        val subtractMinutesToStartFrom =
            pointsCountRandom * DateConstants.NumericConstants.FIVE_MINUTES
        val startFrom = now.minus(subtractMinutesToStartFrom, ChronoUnit.MINUTES)
        val dateFormat = DateTimeFormatter.ofPattern(DateConstants.FormatConstants.MINUTES_FORMAT)
        var tempDate = startFrom

        val points = (0..pointsCountRandom).map { index ->
            val pointId = index + 1
            tempDate =
                tempDate.plus(DateConstants.NumericConstants.FIVE_MINUTES, ChronoUnit.MINUTES)
            val pointDateFormatted = tempDate.format(dateFormat)
            val pointValueRandom = Random.nextInt(0, 99) + Random.nextDouble()

            GraphPointDto(
                id = pointId,
                date = pointDateFormatted,
                value = pointValueRandom
            )
        }
        val graph = GraphDto(points = points)
        return Success(graph)
    }
}