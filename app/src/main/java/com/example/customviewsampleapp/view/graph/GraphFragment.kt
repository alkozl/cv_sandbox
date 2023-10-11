package com.example.customviewsampleapp.view.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.customviewsampleapp.databinding.FragmentGraphBinding
import kotlinx.coroutines.launch

class GraphFragment : Fragment() {
    private val graphViewModel: GraphViewModel by viewModels {
        GraphViewModel.Factory
    }

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            graphViewModel
                .graphUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    handleGraphUiState(it)
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return  binding.root
    }

    private fun handleGraphUiState(graphUiState: GraphUiState) {

    }
}