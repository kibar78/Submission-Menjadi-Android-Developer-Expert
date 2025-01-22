package com.example.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.utils.ResultState
import com.example.dicodingevent.databinding.ActivityFavoriteBinding
import com.example.dicodingevent.ui.adapter.FavoritesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Favorite"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoritesAdapter()
        binding.rvFavoriteEvents.adapter = adapter
        binding.rvFavoriteEvents.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteEvents.setHasFixedSize(true)

        lifecycleScope.launch {
            favoriteViewModel.listFavorite.collect { state ->
                when (state) {
                    is ResultState.Loading -> showLoading(true)
                    is ResultState.Success -> {
                        setUpListFavorites(state.data)
                        showLoading(false)
                        showNoFavoriteMessage(state.data.isEmpty())
                    }
                    is ResultState.Error ->{
                        showLoading(false)
                        showToast(state.error)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (favoriteViewModel.listFavorite.value !is ResultState.Success){
            favoriteViewModel.getAllFavorite()
        }
    }

    private fun setUpListFavorites(favorite: List<Favorite>){
        adapter.submitList(favorite)
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoFavoriteMessage(isEmpty: Boolean) {
        binding.tvInfo.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}