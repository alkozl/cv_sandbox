package com.example.customviewsampleapp.view.widget.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withTranslation
import com.example.customviewsampleapp.R
import com.example.customviewsampleapp.model.ui.GraphPointUi
import com.example.customviewsampleapp.model.ui.GraphUi


class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //region Константы
    companion object Constants {
        private const val FIRST_POINTER_ID = 0
    }

    //region Оси

    /**
     * Ширина линий для отрисовки осей
     * */
    private val axisStrokeWidth = resources.getDimension(R.dimen.graphView_axisStrokeWidth)

    /**
     * Цвет линий для отрисовки осей
     * */
    private val axisStrokeColor = context.getColor(R.color.graphView_defaultAxisColor)

    /**
     * Длина интервала по оси X
     * */
    private val axisXIntervalWidth = resources.getDimension(R.dimen.graphView_axisXIntervalWidth)

    /**
     * Ширина линий для отрисовки интервалов
     * */
    private val axisIntervalSize = resources.getDimension(R.dimen.graphView_axisIntervalSize)

    /**
     * Paint для отрисовки линий для осей
     * */
    private val axisPaint = Paint().apply {
        strokeWidth = axisStrokeWidth
        style = Paint.Style.STROKE
        color = axisStrokeColor
    }

    //endregion

    //region Точки

    /**
     * Paint для отрисовки точек
     * */
    private val pointPaint = Paint().apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.graphView_defaultPointColor)
    }

    /**
     * Paint для отрисовки линий, соединяющий точки
     * */
    private val pointPathPaint = Paint().apply {
        strokeWidth = resources.getDimension(R.dimen.graphView_pointsPathStrokeWidth)
        style = Paint.Style.STROKE
        color = context.getColor(R.color.graphView_defaultPointColor)
    }

    /**
     * Ширина самих точек
     * */
    private val pointWidth = resources.getDimension(R.dimen.graphView_pointWidth)

    //endregion

    //region Фон

    /**
     * Цвет для фона графа
     * */
    private val backgroundColor = context.getColor(R.color.graphView_defaultBackgroundColor)

    /**
     * Paint для отрисовки фона графа
     * */
    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = backgroundColor
    }

    //endregion

    //region Данные

    /**
     * UI модель для построения графа
     * */
    private var graph = GraphUi()

    /**
     * Максимальное значение среди точек
     * */
    private val graphMaxValue get() = graph.maxValue.value

    //endregion

    //region Данные View

    // todo Сделать адаптируемое значение,если высота и ширина не влезают
    /**
     * Отступ слева для пространства для оси X и шкалы оси Y
     * */
    private val minXWithPadding get() = resources.getDimension(R.dimen.graphView_axisXPadding)

    /**
     * Отступ справа для симметрии с отступом слева по оси X
     * */
    private val maxYWithPadding get() = height - minYWithPadding

    /**
     * Отступ сверху и снизу по высоте
     * */
    private val minYWithPadding get() = resources.getDimension(R.dimen.graphView_axisYPadding)

    /**
     * Длина оси Y
     * */
    private val yRange get() = maxYWithPadding - minYWithPadding

    /**
     * Ширина всего графа с учетом горизонтальных отступов, ширины всей шкалы X и добавочной ширины
     * одного интервала X для того, чтобы последняя точка была на предпоследней шкале
     * */
    private val allGraphWidth: Float
        get() = minXWithPadding * 2 + xAllIntervalWidth + axisXIntervalWidth

    /**
     * Ширина оси X
     * */
    private val xAllIntervalWidth get() = graph.points.size * axisXIntervalWidth

    /**
     * Количество интервалов для оси X, равно количеству точек
     * */
    private val xIntervalCount get() = graph.points.size

    // todo Сделать адаптируемым по высоте, в дальнейшем так же адаптировать под значения точек
    /**
     * Количство интервалос для оси Y
     * */
    private val yIntervalCount = 10


    //endregion

    // region Вспомогательные сущности для обработки Touch эвентов

    /**
     * Точка с данными о последнем touch событии
     * */
    private val lastPoint = PointF()

    /**
     * Id указателя последнего touch события
     * */
    private var lastPointerId = 0

    /**
     * Переменная для хранения сдвигов пальцем ([lastPoint]), для прокрутки графика по горизонтали
     * */
    private var lastTranslationX = 0f

    // endregion

    /**
     * Основной метод для записи данных графа
     * @param graph - UI модель графа
     * */
    fun setGraph(graph: GraphUi) {
        this.graph = graph

        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            // Если размер неопределен, занимаем ширину по всей длине графа
            allGraphWidth.toInt()
        } else {
            // Иначе занимаем столько, сколько возможно из предоставленного значения родителем
            MeasureSpec.getSize(widthMeasureSpec)
        }

        // Занимаем столько, сколько возможно из предоставленного значения родителем
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        // Сначала рисуем фон
        canvas.drawBackground()

        // Рисуем оси с интервалами и точки со сдвигом, если пользователь сдвинул граф по горизонтали
        canvas.withTranslation(lastTranslationX, 0f) {
            drawAxis()
            drawPoints()
        }
    }

    /**
     * Метод отрисовки фона графа
     * */
    private fun Canvas.drawBackground() {
        drawColor(backgroundPaint.color)
    }

    /**
     * Метод отрисовки осей графа
     * */
    private fun Canvas.drawAxis() {
        drawXAxis()
        drawYAxis()
    }

    /**
     * Метод отрисовки оси X графа
     * */
    private fun Canvas.drawXAxis() {
        // Старт оси X по оси X
        val axisXStartX = minXWithPadding

        // Старт оси X по оси Y
        val axisXStartY = maxYWithPadding

        // Конец оси X по оси X
        val axisXEndX = minXWithPadding + xAllIntervalWidth + axisXIntervalWidth

        // Конец оси X по оси Y
        val axisXEndY = maxYWithPadding

        // Рисуем ось X
        drawLine(axisXStartX, axisXStartY, axisXEndX, axisXEndY, axisPaint)

        // Координата по оси Y, с которой начинает рисоваться интервал по оси X
        val axisXIntervalStartY = maxYWithPadding - axisIntervalSize

        // Координата по оси Y, на которой заканчивает рисоваться интервал по оси X
        val axisXIntervalEndY = maxYWithPadding + axisIntervalSize

        // Рисуем интервалы для оси X. Начинаем с единицы, так как в точке (0,0) не нужно отрисовывать
        // интервал. Заканчиваем значением на единицу больше, чтобы последняя точка рисовалась предпоследней
        for (interval in 1..xIntervalCount + 1) {
            val axisXIntervalX = axisXStartX + interval * axisXIntervalWidth

            // Отрисовка интервала
            drawLine(
                axisXIntervalX,
                axisXIntervalStartY,
                axisXIntervalX,
                axisXIntervalEndY,
                axisPaint
            )
        }
    }

    /**
     * Метод отрисовки оси Y графа
     * */
    private fun Canvas.drawYAxis() {
        // Старт оси Y по оси X
        val axisYStartX = minXWithPadding

        // Старт оси Y по оси Y
        val axisYStartY = maxYWithPadding

        // Конец оси Y по оси X
        val axisYEndX = minXWithPadding

        // Конец оси Y по оси Y
        val axisYEndY = minYWithPadding

        // Рисуем ось Y
        drawLine(axisYStartX, axisYStartY, axisYEndX, axisYEndY, axisPaint)

        // Координата по оси X, с которой начинает рисоваться интервал по оси Y
        val axisYIntervalStartX = minXWithPadding - axisIntervalSize

        // Координата по оси X, на которой заканчивает рисоваться интервал по оси Y
        val axisYIntervalEndX = minXWithPadding + axisIntervalSize

        // Длина интервала по оис Y
        val yInterval = yRange / yIntervalCount

        // Рисуем интервалы для оси Y. Начинаем с единицы, так как в точке (0,0) не нужно отрисовывать
        // интервал.
        for (interval in 1..yIntervalCount) {
            val axisYIntervalY = axisYStartY - yInterval * interval

            // Отрисовка интервала
            drawLine(
                axisYIntervalStartX,
                axisYIntervalY,
                axisYIntervalEndX,
                axisYIntervalY,
                axisPaint
            )
        }
    }

    /**
     * Метод отрисовки точек графа и линий, соединяющих их
     * */
    private fun Canvas.drawPoints() {
        // Запоминаем прошлую точку, чтобы соединить линией со следующей
        var previousPoint = graph.points.firstOrNull()?.let { mapGraphPointToXY(0, it) }

        // Рисуем точки и линии между ними
        graph.points.forEachIndexed { index, graphPoint ->
            // Маппим значение точки в координаты на графе
            val point = mapGraphPointToXY(index, graphPoint)

            // Рисуем точку
            drawPoint(point)

            previousPoint?.let {
                // Рисуем линию, соединяющую прошлую точку с текущей
                drawLinePointToPoint(it, point)
            }
            previousPoint = point
        }
    }

    /**
     * Метод отрисовки точки на графе
     * */
    private fun Canvas.drawPoint(pointF: PointF) {
        drawCircle(pointF.x, pointF.y, pointWidth, pointPaint)
    }

    /**
     * Метод отрисовки линии, соединяющую одну точку с другой
     * */
    private fun Canvas.drawLinePointToPoint(startPoint: PointF, endPoint: PointF) {
        drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, pointPathPaint)
    }

    /**
     * Метод получения координат точки на графе из ее значения
     * */
    private fun mapGraphPointToXY(index: Int, point: GraphPointUi): PointF {
        // По оси X значение соответствует интервалу X по индкесу
        val x = minXWithPadding + (index + 1) * axisXIntervalWidth

        // По оси Y нужно смапить значение точки в координату на графе
        val y = normalizeValueToY(point.value)
        return PointF(x, y)
    }

    /**
     * Метод нормализации значений точек для маппинг их в координаты по оси Y
     * */
    private fun normalizeValueToY(value: Double): Float {
        // Нормализованное значение точки
        val normalizedValue = (graphMaxValue - value) / graphMaxValue

        // Маппим нормализованное значение в координату Y в границах оси Y
        val normalizedY = normalizedValue * yRange

        // Добавляем отступ сверху
        val finalYCoordinate = minYWithPadding + normalizedY

        return finalYCoordinate.toFloat()
    }

    // region Touch events
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        // Если в событии участвует ровно один указатель, то проверяем событие на сдвиг
        return if (event.pointerCount == 1) processMove(event) else false
    }

    /**
     * Метод для процессинга движений указателя touch события.
     * */
    private fun processMove(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Запоминаем указатель
                lastPoint.set(event.x, event.y)
                lastPointerId = event.getPointerId(FIRST_POINTER_ID)
                true
            }

            MotionEvent.ACTION_MOVE -> {
                // Если размер контента больше размера View - сдвиг доступен
                if (width < allGraphWidth) {
                    val pointerId = event.getPointerId(FIRST_POINTER_ID)

                    // Чтобы избежать скачков, сдвигаем, только если указатель тот же, что и раньше
                    if (lastPointerId == pointerId) {
                        addTranslationX(event.x - lastPoint.x)
                    }

                    // Запоминаем указатель и последнюю точку
                    lastPoint.set(event.x, event.y)
                    lastPointerId = event.getPointerId(FIRST_POINTER_ID)

                    true
                } else {
                    false
                }
            }

            else -> false
        }
    }

    /**
     * Добавление сдвига графа по оси X
     * */
    private fun addTranslationX(dx: Float) {
        // Разница ширины View и фактической ширины по количеству точек графа
        val widthDiff = width - allGraphWidth

        // Вычисляем, насколько вообще можно сдвигать граф по оси X. Если разница ширины View
        // и фактической ширины меньше нуля, то это будет нижней границей для лимита сдвига
        val translationLimit: Float = widthDiff.coerceAtMost(0f)

        // На сколько произошел новый сдвиг
        val newTranslationX = lastTranslationX + dx

        // Если сдвиг находится в границах лимита, то выполняем его. Если он меньше лимита,
        // то ограничиваем лимитом, если больше - блокируем сдвиг
        val transX = newTranslationX.coerceIn(translationLimit, 0f)

        lastTranslationX = transX
        invalidate()
    }

    //endregion
}