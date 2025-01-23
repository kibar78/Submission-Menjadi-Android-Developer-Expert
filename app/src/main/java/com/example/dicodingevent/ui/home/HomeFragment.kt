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
import com.example.dicodingevent.ui.adapter.CarouselAdapter
import com.example.dicodingevent.ui.adapter.EventsAdapter
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var upcomingAdapter: CarouselAdapter
    private lateinit var finishedAdapter: EventsAdapter

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

        upcomingAdapter = CarouselAdapter()
        binding.rvUpcomingEvents.adapter = upcomingAdapter
        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvUpcomingEvents)

        finishedAdapter = EventsAdapter()
        binding.rvFinishedEvents.adapter = finishedAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFinishedEvents.layoutManager = layoutManager

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
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.listUpcomingEvents.value !is ResultState.Success){
            homeViewModel.getupComingEvents()
        }
        if (homeViewModel.listFinishedEvents.value !is ResultState.Success){
            homeViewModel.getFinishedEvents()
        }
    }

    private fun setupUpcomingEvents(upcomingEvents: List<Events?>){
        upcomingAdapter.submitList(upcomingEvents)
    }
    private fun setupFinishedEvents(finishedEvents: List<Events?>){
        finishedAdapter.submitList(finishedEvents)
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