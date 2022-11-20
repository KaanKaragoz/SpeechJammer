package com.mechastudios.speechjammer.model.dashboard

abstract class HomeData(val type: HomeDataType)

data class SpeechJammerData(var name: String?) : HomeData(HomeDataType.SPEECH_JAMMER)

enum class HomeDataType {
    SPEECH_JAMMER
}