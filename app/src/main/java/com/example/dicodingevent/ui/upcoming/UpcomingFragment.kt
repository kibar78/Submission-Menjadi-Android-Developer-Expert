package com.example.dicodingevent.ui.upcoming

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
import com.example.dicodingevent.ui.adapter.EventsAdapter
import com.example.dicodingevent.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val upcomingViewModel: UpcomingViewModel by viewModel()

    private lateinit var adapter: EventsAdapter

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

        adapter = EventsAdapter()
        binding.rvUpcomingEvents.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingEvents.layoutManager = layoutManager
        binding.rvUpcomingEvents.setHasFixedSize(true)

        lifecycleScope.launch {
            upcomingViewModel.listUpcomingEvents.collect{ state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        showLoading(false)
                        setupUpcomingEvents(state.data)
                        showInfoMessage(state.data.isEmpty())
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (upcomingViewModel.listUpcomingEvents.value !is ResultState.Success){
            upcomingViewModel.getupComingEvents()
        }
    }

    private fun setupUpcomingEvents(upcomingEvents: List<Events?>){
        adapter.submitList(upcomingEvents)
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showInfoMessage(isEmpty: Boolean) {
        binding.tvInfo.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}