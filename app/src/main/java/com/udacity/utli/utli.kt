package com.udacity.utli

import android.animation.AnimatorSet
import android.app.DownloadManager
import android.database.Cursor
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

fun DownloadManager.checkStatus(downloadReference: Long ): String {
    val myDownloadQuery = DownloadManager.Query()
    myDownloadQuery.setFilterById(downloadReference)
    val queryCursor: Cursor = this.query(myDownloadQuery)

    var downloadStatus = ""

    if (queryCursor.moveToFirst()) {

        val columnIndex = queryCursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        val status = queryCursor.getInt(columnIndex)
        when (status) {
            DownloadManager.STATUS_PENDING -> downloadStatus = CONSTANTS.PENDING
            DownloadManager.STATUS_PAUSED -> downloadStatus =CONSTANTS.PAUSED
            DownloadManager.STATUS_SUCCESSFUL -> downloadStatus =CONSTANTS.SUCCESSFULR
            DownloadManager.STATUS_RUNNING -> downloadStatus = CONSTANTS.RUNNING
            DownloadManager.STATUS_FAILED -> downloadStatus = CONSTANTS.FAILED
        }
        Log.i("DownStatus", "checkStatus: $downloadStatus")

        return downloadStatus
    }
    Log.i("DownStatus", "checkStatus: $downloadStatus")
    return "there is no return!!"


}

var downloadID: Long = 0


fun AnimatorSet.disableViewDuringAnimation(view: View) = apply {
    doOnStart { view.isEnabled = false }
    doOnEnd { view.isEnabled = true }
}