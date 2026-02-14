package com.hub42.hub42_iptv_player.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hub42.hub42_iptv_player.R
import com.hub42.hub42_iptv_player.data.M3UParser
import com.hub42.hub42_iptv_player.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLiveTv.setOnClickListener {
            loadChannels()
        }

        binding.btnMovies.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de Filmes em breve!", Toast.LENGTH_SHORT).show()
        }

        binding.btnSeries.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de Séries em breve!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadChannels() {
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.loadingAnimation.playAnimation()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val inputStream = assets.open("teste.m3u")
                val content = inputStream.bufferedReader().use { it.readText() }
                val channels = M3UParser.parse(content)
                withContext(Dispatchers.Main) {
                    binding.loadingAnimation.cancelAnimation()
                    binding.loadingAnimation.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Sucesso! ${channels.size} canais carregados.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.loadingAnimation.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Erro ao ler lista: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}