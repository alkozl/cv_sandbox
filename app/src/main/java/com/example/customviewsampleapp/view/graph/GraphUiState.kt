package com.example.customviewsampleapp.view.graph

import com.example.customviewsampleapp.model.domain.Graph
import com.example.customviewsampleapp.utils.coroutine.doOnMain
import com.example.customviewsampleapp.utils.uistate.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

sealed class GraphUiState : UiState {
    data class DataState(
        val graph: Graph
    ) : GraphUiState()

    data object EmptyState : GraphUiState()

    fun updateFlow(
        flow: MutableStateFlow<GraphUiState>,
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.doOnMain {
            flow.emit(this)
        }
    }
}
