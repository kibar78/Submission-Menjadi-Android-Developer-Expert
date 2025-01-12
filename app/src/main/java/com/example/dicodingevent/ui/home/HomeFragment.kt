package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.core.utils.ResultState
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.ui.CarouselAdapter
import com.example.dicodingevent.core.ui.EventsAdapter
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            homeViewModel.listUpcomingEvents.collect { state ->
                when(state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        setupUpcomingEvents(state.data)
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }

        lifecycleScope.launch {
        homeViewModel.listFinishedEvents.collect{ state ->
            when(state) {
                is ResultState.Loading -> showLoading2(true)
                is ResultState.Success -> {
                    setupFinishedEvents(state.data)
                    showLoading2(false)
                }
                is ResultState.Error -> {
                    showLoading2(false)
                    showToast(state.error)
                }
            }
        }
        }

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvUpcomingEvents)

        val layoutManager2 = LinearLayoutManager(requireActivity())
        binding.rvFinishedEvents.layoutManager = layoutManager2
        binding.rvFinishedEvents.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.listUpcomingEvents.value !is ResultState.Success) {
            homeViewModel.getupComingEvents()
        }
        if (homeViewModel.listFinishedEvents.value !is ResultState.Success) {
            homeViewModel.getFinishedEvents()
        }
    }

    private fun setupUpcomingEvents(upcomingEvents: List<Events?>){
        val adapter = CarouselAdapter()
        adapter.submitList(upcomingEvents)
        binding.rvUpcomingEvents.adapter = adapter
    }

    private fun setupFinishedEvents(finishedEvents: List<Events?>){
        val adapter = EventsAdapter()
        adapter.submitList(finishedEvents)
        binding.rvFinishedEvents.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showLoading2(isLoading: Boolean){
        binding.pbLoading2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}