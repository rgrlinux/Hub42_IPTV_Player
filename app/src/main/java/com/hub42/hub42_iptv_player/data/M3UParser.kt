package com.hub42.hub42_iptv_player.data
import com.hub42.hub42_iptv_player.model.Channel

object M3UParser {
    fun parse(content: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        var currentChannelName = ""
        var currentLogo = ""
        var currentGroup = ""

        content.lineSequence().forEach { line ->
            when {
                line.startsWith("#EXTINF") -> {
                    currentChannelName = line.substringAfterLast(",").trim()
                    currentLogo = line.substringAfter("tvg-logo=\"").substringBefore("\"")
                    currentGroup = line.substringAfter("group-title=\"").substringBefore("\"")
                }
                line.startsWith("http") -> {
                    channels.add(Channel(currentChannelName, line.trim(), currentLogo, currentGroup))
                }
            }
        }
        return channels
    }
}