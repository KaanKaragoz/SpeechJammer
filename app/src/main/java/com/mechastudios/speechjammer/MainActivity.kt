package com.mechastudios.speechjammer

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mechastudios.speechjammer.ui.main.DashboardFragment

class MainActivity : AppCompatActivity() {




    private var audioFilePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DashboardFragment.newInstance())
                    .commitNow()

        }
    }

    fun hasMicrophone(): Boolean {
        val pmanager = this.packageManager
        return pmanager.hasSystemFeature(
            PackageManager.FEATURE_MICROPHONE)
    }

    fun requestPermission(permissionType: String, requestCode: Int) {
        val permission = ContextCompat.checkSelfPermission(this,
            permissionType)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(permissionType), requestCode
            )
        }
    }

    private val RECORD_REQUEST_CODE = 101
    private val STORAGE_REQUEST_CODE = 102
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0]
                    != PackageManager.PERMISSION_GRANTED) {


                    Toast.makeText(this,
                        "Record permission required",
                        Toast.LENGTH_LONG).show()
                } else {
                    requestPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_REQUEST_CODE)
                }
                return
            }
            STORAGE_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0]
                    != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                        "External Storage permission required",
                        Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
    private fun audioSetup() {



        audioFilePath = Environment.getExternalStorageDirectory()
            .absolutePath + "/myaudio.3gp"

        requestPermission(Manifest.permission.RECORD_AUDIO,
            RECORD_REQUEST_CODE)

    }


}