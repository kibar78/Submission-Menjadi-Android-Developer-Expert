package com.example.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        val eventItem = intent.getParcelableExtra<ListEventsItem>(EXTRA_ID)
        if (eventItem != null){
            binding.apply {
                toolbar.title = eventItem.name
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
                toolbar.setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvName.text = eventItem.name
                tvSummary.text = eventItem.summary
                tvQuota.text = getString(R.string.sisa_kuota, eventItem.registrants)
                tvBeginTime.text = eventItem.beginTime.toString()
                tvDesc.text = HtmlCompat.fromHtml(
                    eventItem.description.toString(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                Glide.with(this@DetailActivity)
                    .load(eventItem.mediaCover)
                    .into(imgView)

                btnRegister.setOnClickListener {
                    val link = eventItem.link
                    if (link != null){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@DetailActivity,"Url tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else {
            Toast.makeText(this,"Data Tidak Ditemukan", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    companion object{
        const val EXTRA_ID = "event.id"
    }
}