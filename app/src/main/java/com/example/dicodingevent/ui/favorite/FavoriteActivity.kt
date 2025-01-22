package com.example.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.ui.adapter.FavoritesAdapter
import com.example.dicodingevent.databinding.ActivityFavoriteBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Favorite"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavoriteEvents.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteEvents.setHasFixedSize(true)

        favoriteViewModel.getAllFavorite()

        lifecycleScope.launch {
            favoriteViewModel.listFavorite.collect { list ->
                showLoading(true)
                try {
                    showLoading(false)
                    setUpListFavorites(list)
                } catch (e: Exception) {
                    showLoading(false)
                    showToast(e.message.toString())
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        favoriteViewModel.getAllFavorite()
    }

    private fun setUpListFavorites(favorite: List<Favorite>){
        val adapter = FavoritesAdapter()
        adapter.submitList(favorite)
        binding.rvFavoriteEvents.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}