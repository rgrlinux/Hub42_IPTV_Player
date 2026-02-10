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
        // 1. Mostra a animação de carregamento e esconde os botões (opcional)
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.loadingAnimation.playAnimation()

        // 2. Inicia uma Coroutine no thread de IO (Background)
        // Isso é VITAL para não travar o Android 7 com 100k linhas
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Abre o arquivo dos assets
                val inputStream = assets.open("teste.m3u")
                val content = inputStream.bufferedReader().use { it.readText() }

                // Faz o parse da lista usando seu M3UParser
                val channels = M3UParser.parse(content)

                // 3. Volta para o thread principal (Main) para atualizar a tela
                withContext(Dispatchers.Main) {
                    binding.loadingAnimation.cancelAnimation()
                    binding.loadingAnimation.visibility = View.GONE

                    Toast.makeText(
                        this@MainActivity,
                        "Sucesso! ${channels.size} canais carregados.",
                        Toast.LENGTH_LONG
                    ).show()

                    // Aqui você chamaria a próxima tela passando a lista de canais
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