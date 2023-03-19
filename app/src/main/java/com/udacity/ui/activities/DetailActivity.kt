package com.udacity.ui.activities

import android.app.NotificationManager
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.udacity.R
import com.udacity.utli.CONSTANTS
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


val  notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager

        notificationManager.cancelAll()


        val tvFileName = findViewById<TextView>(R.id.textView_detail_fileName)
        val tvStatus = findViewById<TextView>(R.id.tv_detail_status)
        val buttonBackToMain = findViewById<TextView>(R.id.button_back_to_main)

        val dataFromNotification = intent.extras?.getString(CONSTANTS.STATUS)


        if (dataFromNotification != null) {

            val stat = dataFromNotification
            val fName = intent.extras?.getString(CONSTANTS.FILENAME)

            tvFileName.text = fName
            tvStatus.text = stat

            if (stat == CONSTANTS.SUCCESSFULR)
                tvStatus.setTextColor(getColor(R.color.colorPrimaryDark))
            else
                tvStatus.setTextColor(getColor(R.color.colorAccent))
        } else {
            tvStatus.text = getString(R.string.not_mentioned)
            tvFileName.text = getString(R.string.not_mentioned)
        }

        if (dataFromNotification != null) {
            Log.i("data_Check", "onCreate:${intent.extras?.get(CONSTANTS.STATUS)} ")
        }
        buttonBackToMain.setOnClickListener {
         backToMain()
        }


    }





    private fun backToMain(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

}
