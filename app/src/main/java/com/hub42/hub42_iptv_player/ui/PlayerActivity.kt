package com.hub42.hub42_iptv_player.ui
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.hub42.hub42_iptv_player.R

@OptIn(UnstableApi::class)
class PlayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playerView = findViewById(R.id.playerView)
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: ""

        initializePlayer(videoUrl)
    }

    private fun initializePlayer(url: String) {
        if (url.isEmpty()) return

        player = ExoPlayer.Builder(this)
            .setSeekForwardIncrementMs(10000) // Avançar 10s
            .setSeekBackIncrementMs(10000)    // Voltar 10s
            .build().also { exoPlayer ->
                playerView.player = exoPlayer

                // Suporte para HLS (.m3u8), MP4 e MKV
                val mediaItem = MediaItem.fromUri(url)
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.prepare()
                exoPlayer.playWhenReady = true // Começa o vídeo automaticamente
            }
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}