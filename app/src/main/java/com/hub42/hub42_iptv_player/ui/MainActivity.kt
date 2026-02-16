package com.hub42.hub42_iptv_player.ui

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hub42.hub42_iptv_player.R
import com.hub42.hub42_iptv_player.ui.PlayerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        val imgPoster = findViewById<ImageView>(R.id.imgPoster) // Se tiver um ImageView no layout

        // URL correta do poster do Frankenstein (2025)
        val posterUrl = "https://image.tmdb.org/t/p/w780/cXsMxClCcAF1oMwoXZvbKwWoNeS.jpg"
        val videoUrl = "http://cdn.lplaytech.top:80/movie/494974886/940133127/8248869.mp4"

        // Carrega o poster correto para não dar erro 404
        if (imgPoster != null) {
            Glide.with(this).load(posterUrl).into(imgPoster)
        }

        // Busca Hardcode: Qualquer enter abre o vídeo
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val intent = Intent(this, PlayerActivity::class.java).apply {
                    putExtra("VIDEO_URL", videoUrl)
                }
                startActivity(intent)
                true
            } else false
        }
    }
}