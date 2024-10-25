package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.adapter.EventsAdapter
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.databinding.FragmentUpcomingBinding
import com.example.dicodingevent.utils.ResultState
import com.example.dicodingevent.utils.ViewModelFactory

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val upcomingViewModel by viewModels<UpcomingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upcomingViewModel.listUpcomingEvents.observe(viewLifecycleOwner){state->
            when(state){
                is ResultState.Loading->{
                    showLoading(true)
                }
                is ResultState.Success->{
                    showLoading(false)
                    setupUpcomingEvents(state.data)
                }
                is ResultState.Error->{
                    showLoading(false)
                    showToast(state.error)
                }
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingEvents.layoutManager = layoutManager
        binding.rvUpcomingEvents.setHasFixedSize(true)

        upcomingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        if (upcomingViewModel.listUpcomingEvents.value !is ResultState.Success){
            upcomingViewModel.getupComingEvents()
        }

    }

    private fun setupUpcomingEvents(upcomingEvents: List<ListEventsItem?>){
        val adapter = EventsAdapter()
        adapter.submitList(upcomingEvents)
        binding.rvUpcomingEvents.adapter = adapter
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