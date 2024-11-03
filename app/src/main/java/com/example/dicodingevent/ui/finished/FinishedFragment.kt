package com.example.dicodingevent.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dicodingevent.adapter.EventsAdapter
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.databinding.FragmentFinishedBinding
import com.example.dicodingevent.utils.ResultState
import com.example.dicodingevent.utils.ViewModelFactory

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val finishedViewModel by viewModels<FinishedViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

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
        finishedViewModel.listFinishedEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
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
        val layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding.rvFinishedEvents.layoutManager = layoutManager
        binding.rvFinishedEvents.setHasFixedSize(true)

        finishedViewModel.getFinishedEvents()
    }

    override fun onResume() {
        super.onResume()
        finishedViewModel.getFinishedEvents()
    }

    private fun setupFinishedEvents(upcomingEvents: List<ListEventsItem?>){
        val adapter = EventsAdapter()
        adapter.submitList(upcomingEvents)
        binding.rvFinishedEvents.adapter = adapter

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