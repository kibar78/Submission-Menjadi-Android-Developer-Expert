package com.example.dicodingevent.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.core.domain.usecase.EventsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams){

    private var resultStatus: Result? = null
    //private val apiService = ApiConfig.getApiService()

    private val eventsUseCase: EventsUseCase by inject(EventsUseCase::class.java)

    override fun doWork(): Result {
        return try {
            runBlocking {
                getDailyReminder()
                Result.success()
            }
        }catch (e: Exception){
            Result.failure()
        }
    }

    private suspend fun getDailyReminder(): Result{
        val response = eventsUseCase.getDailyReminder(-1,1)
        if (response.isNotEmpty()){
            val event = response[0]
            event.name.let {
                val beginTime = event.beginTime
                CoroutineScope(Dispatchers.IO).launch {
                    val imageBitmap = Glide.with(applicationContext)
                        .asBitmap()
                        .load(event.imageLogo)
                        .submit()
                        .get()
                    withContext(Dispatchers.Main){
                        showNotification(it, beginTime, imageBitmap)
                    }
                }
            }

        }
        return resultStatus as Result
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(title: String, beginTime: String?, image: Bitmap?){
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_upcoming_24dp)
            .setContentTitle(title)
            .setContentText(beginTime)
            .setLargeIcon(image)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(image))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    companion object{
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }
}