package com.hub42.hub42_iptv_player.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hub42.hub42_iptv_player.R
import com.hub42.hub42_iptv_player.data.M3UParser
import com.hub42.hub42_iptv_player.ui.adapter.MovieAdapter

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val rvMovies = findViewById<RecyclerView>(R.id.rvMovies)

        // Setup Grid (5 columns for TV/Landscape)
        rvMovies.layoutManager = GridLayoutManager(this, 5)

        // Mock data loading
        loadMoviesData(rvMovies)
    }

    private fun loadMoviesData(recyclerView: RecyclerView) {
        val dummyM3u = """
            #EXTM3U
            #EXTINF:-1 tvg-logo="https://image.tmdb.org/t/p/w500/8UlT9vubO79el933pAdfsU3qI6I.jpg" group-title="Movies",The Flash
            http://example.com/flash.mp4
            #EXTINF:-1 tvg-logo="https://image.tmdb.org/t/p/w500/vB6tS3P2HZlg9Yv90fsTLyhNi9c.jpg" group-title="Movies",Avatar
            http://example.com/avatar.mkv
        """.trimIndent()

        val movies = M3UParser().parse(dummyM3u, "MOVIE")
        recyclerView.adapter = MovieAdapter(movies) { movie ->
            // Here we will call the Video Player later
        }
    }
}