package com.example.dicodingevent.ui.search

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
import com.example.dicodingevent.databinding.FragmentSearchBinding
import com.example.dicodingevent.ui.search.SearchViewModel.Companion.ACTIVE
import com.example.dicodingevent.ui.search.SearchViewModel.Companion.QUERY
import com.example.dicodingevent.utils.ResultState
import com.example.dicodingevent.utils.ViewModelFactory

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.listSearchEvents.observe(viewLifecycleOwner){state->
            when(state){
                is ResultState.Loading->{
                    showLoading(true)
                }
                is ResultState.Success->{
                    showLoading(false)
                    setupSearchEvents(state.data)
                }
                is ResultState.Error->{
                    showLoading(false)
                    showToast(state.error)
                }
            }
        }
        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchViewModel.searchEvents(ACTIVE,searchView.text.toString())
                    false
                }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvSearchEvents.layoutManager = layoutManager
        binding.rvSearchEvents.setHasFixedSize(true)

        if (searchViewModel.listSearchEvents.value !is ResultState.Success) {
            searchViewModel.searchEvents(ACTIVE, QUERY)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    private fun setupSearchEvents(searchEvents: List<ListEventsItem?>){
        val adapter = EventsAdapter()
        adapter.submitList(searchEvents)
        binding.rvSearchEvents.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}