package com.example.dicodingevent.ui.settings

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.dicodingevent.R
import com.example.dicodingevent.databinding.ActivitySettingsBinding
import com.example.dicodingevent.reminder.MyWorker
import com.example.dicodingevent.core.ui.ViewModelFactory
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.title = "Settings"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        workManager = WorkManager.getInstance(this)
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java,24,TimeUnit.HOURS)
            .build()

        viewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
        viewModel.getNotificationSetting().observe(this){ isNotificationActive: Boolean->
            binding.switchNotification.isChecked = isNotificationActive
        }

        binding.switchTheme.setOnClickListener(this)
        binding.switchNotification.setOnClickListener(this)
    }

    private fun startPeriodicTask(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java,24,TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(periodicWorkRequest)
    }

    private fun cancelPeriodicTask(){
        workManager.cancelWorkById(periodicWorkRequest.id)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.switch_theme -> {
                val isChecked = binding.switchTheme.isChecked
                viewModel.saveThemeSetting(isChecked)
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            R.id.switch_notification ->{
                WorkManager.getInstance(this).enqueue(periodicWorkRequest)
                val isChecked = binding.switchNotification.isChecked
                viewModel.saveNotificationSetting(isChecked)
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    startPeriodicTask()
                } else {
                    cancelPeriodicTask()
                }
            }
        }
    }
}