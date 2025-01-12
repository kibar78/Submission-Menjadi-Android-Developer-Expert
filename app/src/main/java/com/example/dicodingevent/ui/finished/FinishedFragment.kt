package com.example.dicodingevent.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingevent.core.utils.ResultState
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.ui.EventsAdapter
import com.example.dicodingevent.databinding.FragmentFinishedBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val finishedViewModel: FinishedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            finishedViewModel.listFinishedEvents.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        setupFinishedEvents(state.data)
                        showLoading(false)
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFinishedEvents.layoutManager = layoutManager
        binding.rvFinishedEvents.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        if (finishedViewModel.listFinishedEvents.value !is ResultState.Success){
            finishedViewModel.getFinishedEvents()
        }
    }

    private fun setupFinishedEvents(finishedEvents: List<Events?>){
        val adapter = binding.rvFinishedEvents.adapter as? EventsAdapter
        if (adapter != null) {
            adapter.submitList(finishedEvents)
        } else {
            val newAdapter = EventsAdapter()
            newAdapter.submitList(finishedEvents)
            binding.rvFinishedEvents.adapter = newAdapter
        }

    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}