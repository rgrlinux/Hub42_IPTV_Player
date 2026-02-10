package com.hub42.hub42_iptv_player.model

data class Channel(
    val name: String,
    val url: String,
    val logoUrl: String? = null,
    val category: String? = null,
    val type: String = "LIVE"
)