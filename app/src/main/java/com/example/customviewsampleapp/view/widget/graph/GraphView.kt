package com.example.customviewsampleapp.view.widget.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.example.customviewsampleapp.R
import com.example.customviewsampleapp.model.ui.GraphPointUi
import com.example.customviewsampleapp.model.ui.GraphUi


class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //region Константы
    object Constants {
        const val AXIS_X_PERCENT_PADDING = 0.15F
        const val AXIS_Y_PERCENT_PADDING = 0.15F
    }
    //endregion

    //region Переменные-аттрибуты

    //endregion

    //region Граф

    //endregion

    //region Оси

    private val axisStrokeWidth = resources.getDimension(R.dimen.graphView_axisStrokeWidth)
    private val axisStrokeColor = context.getColor(R.color.graphView_defaultAxisColor)
    private val axisIntervalWidth = resources.getDimension(R.dimen.graphView_axisIntervalWidth)
    private val axisIntervalSize = resources.getDimension(R.dimen.graphView_axisIntervalSize)

    private val axisPaint = Paint().apply {
        strokeWidth = axisStrokeWidth
        style = Paint.Style.STROKE
        color = axisStrokeColor
    }

    private val yRange get() = maxYWithPadding - minYWithPadding

    //endregion

    //region Точки

    private val pointPaint = Paint().apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.graphView_defaultPointColor)
    }

    private val pointPathPaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.graphView_pointsPathStrokeWidth)
        style = Paint.Style.STROKE
        color = context.getColor(R.color.graphView_defaultPointColor)
    }

    private val pointWidth = resources.getDimension(R.dimen.graphView_pointWidth)

    //endregion

    //region Фон

    private val backgroundStrokeWidth =
        resources.getDimension(R.dimen.graphView_backgroundStrokeWidth)
    private val backgroundStrokeColor = context.getColor(R.color.graphView_defaultBackgroundColor)

    private val backgroundColor = context.getColor(R.color.graphView_defaultBackgroundColor)

    private val backgroundStrokePaint = Paint().apply {
        strokeWidth = backgroundStrokeWidth
        style = Paint.Style.STROKE
        color = backgroundStrokeColor
    }

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = backgroundColor
    }

    //endregion

    //region Данные

    private var graph = GraphUi()
    private val graphMaxValue get() = graph.maxValue.value

    //endregion

    //region Данные View
    private val minXWithPadding get() = width * Constants.AXIS_X_PERCENT_PADDING
    private val maxYWithPadding get() = height - height * Constants.AXIS_Y_PERCENT_PADDING
    private val minYWithPadding get() = height * Constants.AXIS_Y_PERCENT_PADDING

    //endregion

    fun setGraph(graph: GraphUi) {
        this.graph = graph

        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Занимаем столько, сколько родитель предоставит
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawAxis(canvas)
        drawPoints(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(backgroundPaint.color)
    }

    private fun drawAxis(canvas: Canvas) {
        // Рисуем ось X
        val axisXStartX = minXWithPadding
        val axisXStartY = maxYWithPadding
        val axisXEndX = width.toFloat()
        val axisXEndY = maxYWithPadding
        canvas.drawLine(axisXStartX, axisXStartY, axisXEndX, axisXEndY, axisPaint)

        val xAllIntervalWidth = graph.points.count() * axisIntervalWidth
        val xIntervalCount = if (xAllIntervalWidth < width) {
            // Если суммарная длина интервалов по точкам меньше ширины всей оси X, то рисуем
            // интервалы до конца ширины
            width / axisIntervalWidth.toInt()
        } else {
            // Иначе рисуем по интервалы по количествам точек
            graph.points.count()
        }

        // Рисуем интервалы для оси X
        for (interval in 0..xIntervalCount) {
            val intervalAddition = interval + 1
            val axisXIntervalX = axisXStartX + intervalAddition * axisIntervalWidth
            val axisXIntervalStartY = maxYWithPadding - axisIntervalSize
            val axisXIntervalEndY = maxYWithPadding + axisIntervalSize
            canvas.drawLine(
                axisXIntervalX,
                axisXIntervalStartY,
                axisXIntervalX,
                axisXIntervalEndY,
                axisPaint
            )
        }

        // Рисуем ось Y
        val axisYStartX = minXWithPadding
        val axisYStartY = maxYWithPadding
        val axisYEndX = minXWithPadding
        val axisYEndY = minYWithPadding - axisIntervalWidth
        canvas.drawLine(axisYStartX, axisYStartY, axisYEndX, axisYEndY, axisPaint)

        val yIntervalCount = yRange.toInt() / axisIntervalWidth.toInt()

        // Рисуем интервалы для оси Y
        for (interval in 0..<yIntervalCount) {
            val intervalAddition = interval + 1
            val axisYIntervalY = axisYStartY - intervalAddition * axisIntervalWidth
            val axisYIntervalStartX = minXWithPadding - axisIntervalSize
            val axisYIntervalEndX = minXWithPadding + axisIntervalSize
            canvas.drawLine(
                axisYIntervalStartX,
                axisYIntervalY,
                axisYIntervalEndX,
                axisYIntervalY,
                axisPaint
            )
        }
    }

    private fun drawPoints(canvas: Canvas) {
        var previousPoint = graph.points.firstOrNull()?.let { mapGraphPointToXY(0, it) }
        graph.points.forEachIndexed { index, graphPoint ->
            val point = mapGraphPointToXY(index, graphPoint)
            drawPoint(canvas, point)
            previousPoint?.let {
                canvas.drawLine(it.x, it.y, point.x, point.y, pointPathPaint)
            }
            previousPoint = point
        }
    }

    private fun drawPoint(canvas: Canvas, pointF: PointF) {
        canvas.drawCircle(pointF.x, pointF.y, pointWidth, pointPaint)
    }

    private fun mapGraphPointToXY(index: Int, point: GraphPointUi): PointF {
        val x = minXWithPadding + (index + 1) * axisIntervalWidth
        val y = normalizeValueToY(point.value)
        return PointF(x, y)
    }

    private fun normalizeValueToY(value: Double): Float {
        val normalizedValue = (graphMaxValue - value) / graphMaxValue
        val normalizedY = normalizedValue.toFloat() * yRange
        val finalYCoordinate = minYWithPadding + normalizedY
        return finalYCoordinate
    }
}