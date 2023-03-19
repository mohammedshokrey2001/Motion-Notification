package com.udacity.ui.activities

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.udacity.R
import com.udacity.notifcation.createChannel
import com.udacity.notifcation.sendNotification
import com.udacity.utli.CONSTANTS
import com.udacity.utli.checkStatus
import com.udacity.utli.downloadID
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var radioButton: RadioButton

    var downloadFileName: String = ""

    private lateinit var downloadManagerVar: DownloadManager
    private lateinit var notificationManagerVar: NotificationManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        registerReceiver(dataReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

         var selectionGroup: RadioGroup? = null


        setContentView(R.layout.activity_main)


        custom_button_load.setOnClickListener {
            custom_button_load.start()
            downloadData(url = CONSTANTS.URL1)

            val intSelectButton: Int = selectionGroup!!.checkedRadioButtonId
            if (selectionGroup!!.checkedRadioButtonId == -1) {
                Toast.makeText(applicationContext, getString(R.string.no_selection), Toast.LENGTH_LONG).show()


            } else {
                radioButton = findViewById(intSelectButton)
                onItemSelected(radioButton)
            }
        }




         registerReceiver(dataReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

         selectionGroup = findViewById(R.id.radioGroup)

        notificationManagerVar = ContextCompat.getSystemService(this,
        NotificationManager::class.java) as NotificationManager

       notificationManagerVar.createChannel(getString(R.string.channel_id),
           getString(R.string.channel_name),
           getString(R.string.notification_description))


    }

    private fun onItemSelected(view: View) {

        when (view.id) {
            R.id.radioButton_main_loadApp -> {
                downloadData(CONSTANTS.URL1)
                downloadFileName = getString(R.string.download_using_loadapp)
            }

            R.id.radioButton_main_retrofit -> {
                downloadData(CONSTANTS.URL3)
                downloadFileName = getString(R.string.download_using_refrofit)
            }

            R.id.radioButton_main_glide -> {

                downloadData(CONSTANTS.URL2)

                downloadFileName = getString(R.string.download_using_glide)
            }


        }
    }


    private val dataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val processId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            context.let {
                if (it != null) {
                    notificationManagerVar.sendNotification(it,getString(R.string.notification_description),downloadFileName,
                        downloadManagerVar. checkStatus(processId!!)
                    )
                }

            }
            Log.i("receive", "onReceive: $downloadFileName")
            custom_button_load.stop()
        }



    }

    private fun downloadData(url:String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

         downloadManagerVar = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManagerVar.enqueue(request)
    }




}
