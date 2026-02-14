package com.hub42.hub42_iptv_player.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hub42.hub42_iptv_player.ui.MainActivity
import com.hub42.hub42_iptv_player.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val lottieView = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.lottieAnimation)


        lottieView.renderMode = com.airbnb.lottie.RenderMode.SOFTWARE

        lottieView.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 4000)
    }
}

