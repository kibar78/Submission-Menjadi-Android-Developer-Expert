package com.example.dicodingevent.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.adapter.FavoritesAdapter
import com.example.dicodingevent.data.local.FavoriteEntity
import com.example.dicodingevent.databinding.ActivityFavoriteBinding
import com.example.dicodingevent.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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

        favoriteViewModel.listFavorite.observe(this){list->
            showLoading(true)
            try {
                showLoading(false)
                setUpListFavorites(list)
            }catch (e: Exception){
                showLoading(false)
                showToast(e.message.toString())
            }
        }
    }

    override fun onResume() {
        favoriteViewModel.getAllFavorite()
        super.onResume()
    }

    private fun setUpListFavorites(favorite: List<FavoriteEntity>){
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