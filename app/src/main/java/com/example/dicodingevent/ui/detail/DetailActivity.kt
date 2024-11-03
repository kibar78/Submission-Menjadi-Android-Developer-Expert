package com.example.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.data.network.response.Event
import com.example.dicodingevent.databinding.ActivityDetailBinding
import com.example.dicodingevent.utils.ResultState
import com.example.dicodingevent.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var favoriteStatus : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val itemId = intent.getIntExtra(EXTRA_ID,0)

        viewModel.detailEvent.observe(this){result->
            when(result){
                is ResultState.Loading-> showLoading(true)
                is ResultState.Success->{
                    showLoading(false)
                    setDetailEvent(result.data)
                }
                is ResultState.Error->{
                    showToast(result.error)
                    showLoading(false)
                }
            }
        }
        viewModel.getFavoriteById(itemId.toString()).observe(this) { favorite ->
            favoriteStatus = favorite != null
            updateFavoriteIcon(favoriteStatus)
        }
        binding.fabAddFavorite.setOnClickListener {
            val event = (viewModel.detailEvent.value as? ResultState.Success)?.data
            if (event != null) {
                if (favoriteStatus) {
                    viewModel.deleteFavorite(event)
                } else {
                    viewModel.addFavorite(event)
                }
            }
        }
        viewModel.getDetailEvent(itemId)
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.fabAddFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24
        )
    }

    private fun setDetailEvent(detailEvent: Event?){
            binding.apply {
                toolbar.title = detailEvent?.name
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
                toolbar.setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvName.text = detailEvent?.name
                tvSummary.text = detailEvent?.summary
                tvQuota.text = getString(R.string.sisa_kuota, detailEvent?.registrants)
                tvBeginTime.text = detailEvent?.beginTime.toString()
                tvDesc.text = HtmlCompat.fromHtml(
                    detailEvent?.description.toString(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                Glide.with(this@DetailActivity)
                    .load(detailEvent?.mediaCover)
                    .into(imgView)
                btnRegister.setOnClickListener {
                    val link = detailEvent?.link
                    if (link != null){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@DetailActivity,"Url tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
    companion object{
        const val EXTRA_ID = "event.id"
    }


}