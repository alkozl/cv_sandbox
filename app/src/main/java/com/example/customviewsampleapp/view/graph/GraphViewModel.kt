package com.example.customviewsampleapp.view.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.customviewsampleapp.app.App
import com.example.customviewsampleapp.domain.graph.GetGraphUseCase
import com.example.customviewsampleapp.model.ui.GraphUi
import com.example.customviewsampleapp.utils.coroutine.launchOnMain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GraphViewModel(
    private val getGraphUseCase: GetGraphUseCase
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                (this[APPLICATION_KEY] as App).appComponent.injectGraphViewModel()
            }
        }
    }

    private val _graphUiState: MutableStateFlow<GraphUiState> =
        MutableStateFlow(GraphUiState.EmptyState)
    val graphUiState = _graphUiState.asStateFlow()

    init {
        getGraph()
    }

    private fun getGraph() {
        launchOnMain {
            getGraphUseCase()
                .onSuccess(::updateGraphDataState)
        }
    }

    private suspend fun updateGraphDataState(graph: GraphUi) {
        GraphUiState.DataState(graph)
            .updateFlow(_graphUiState)
    }
}