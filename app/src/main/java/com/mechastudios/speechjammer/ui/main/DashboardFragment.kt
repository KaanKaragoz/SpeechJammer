package com.mechastudios.speechjammer.ui.main

import android.Manifest
import android.media.*
import android.media.AudioTrack.Builder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mechastudios.speechjammer.MainActivity
import com.mechastudios.speechjammer.databinding.FragmentDashboardBinding
import com.mechastudios.speechjammer.ui.BaseFragment
import kotlinx.coroutines.*


class DashboardFragment : BaseFragment() {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DashboardViewModel

    private var isPlaying = false
    private lateinit var audioTrack: AudioTrack

    private val RECORD_REQUEST_CODE = 101
    private val STORAGE_REQUEST_CODE = 102
    var shortAudioList = ArrayList<ShortArray>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        audioSetup()

        binding.record.setOnClickListener {
            isActive = true
            CoroutineScope(Dispatchers.IO).launch {
                recordAudio()
            }
        }

        binding.play.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                playAudio()
            }
        }

        binding.stop.setOnClickListener {

            stopAudio()
        }
    }

    fun audioSetup() {
        if (!(activity as MainActivity).hasMicrophone()) {
            binding.stop.isEnabled = true
            binding.play.isEnabled = true
            binding.record.isEnabled = false
        } else {
            binding.play.isEnabled = true
            binding.stop.isEnabled = true
        }

        (activity as MainActivity).requestPermission(
            Manifest.permission.RECORD_AUDIO,
            RECORD_REQUEST_CODE
        )
    }

    var isActive: Boolean = false

    fun recordAudio() {

        val intRecordSampleRate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC)
        val intBufferSize = AudioRecord.getMinBufferSize(
            intRecordSampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
        )

        val shortAudioData = ShortArray(intBufferSize)

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            intRecordSampleRate,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            intBufferSize
        )

        audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            intRecordSampleRate,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            intBufferSize,
            AudioTrack.MODE_STREAM,
        )

        audioTrack.playbackRate = intRecordSampleRate
        audioRecord.startRecording()
        audioTrack.play()

        while (isActive) {
            audioRecord.read(shortAudioData, 0, shortAudioData.size)
            shortAudioList.add(shortAudioData)
            audioTrack.write(shortAudioData, 0, shortAudioData.size)
        }
    }

    fun stopAudio() {
        isActive = false
    }

    fun playAudio() {
        isPlaying = true

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}