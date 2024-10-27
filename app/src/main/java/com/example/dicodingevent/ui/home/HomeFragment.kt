package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.adapter.CarouselAdapter
import com.example.dicodingevent.adapter.EventsAdapter
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.utils.ResultState
import com.example.dicodingevent.utils.ViewModelFactory
import com.google.android.material.carousel.CarouselSnapHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

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

        homeViewModel.listUpcomingEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
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
        homeViewModel.listFinishedEvents.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.Loading -> {
                    showLoading2(true)
                }
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

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvUpcomingEvents)

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        if (homeViewModel.listUpcomingEvents.value !is ResultState.Success){
            homeViewModel.getupComingEvents()
        }

        val layoutManager2 = LinearLayoutManager(requireActivity())
        binding.rvFinishedEvents.layoutManager = layoutManager2
        binding.rvFinishedEvents.setHasFixedSize(true)

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading2(it)
        }
        if (homeViewModel.listFinishedEvents.value !is ResultState.Success){
            homeViewModel.getFinishedEvents()
        }
    }

    private fun setupUpcomingEvents(upcomingEvents: List<ListEventsItem?>){
        val adapter = CarouselAdapter()
        adapter.submitList(upcomingEvents)
        binding.rvUpcomingEvents.adapter = adapter
    }

    private fun setupFinishedEvents(upcomingEvents: List<ListEventsItem?>){
        val adapter = EventsAdapter()
        adapter.submitList(upcomingEvents)
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