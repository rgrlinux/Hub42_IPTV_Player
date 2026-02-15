package com.hub42.hub42_iptv_player.ui

import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.hub42.hub42_iptv_player.R
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI Components
        val btnLiveTv = findViewById<CardView>(R.id.btnLiveTv)
        val btnMovies = findViewById<CardView>(R.id.btnMovies)
        val btnSeries = findViewById<CardView>(R.id.btnSeries)
        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        val txtMac = findViewById<TextView>(R.id.txtMac)

        // Device Identifier (Virtual MAC)
        setupDeviceId(txtMac)

        // Setup Focus Effects
        applyFocusScaleAnimation(btnLiveTv)
        applyFocusScaleAnimation(btnMovies)
        applyFocusScaleAnimation(btnSeries)
        applyFocusScaleAnimation(edtSearch)

        // Search Logic
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(edtSearch.text.toString())
                true
            } else false
        }

        // Click Listeners
        btnLiveTv.setOnClickListener { openCategory("LIVE") }
        btnMovies.setOnClickListener { openCategory("MOVIES") }
        btnSeries.setOnClickListener { openCategory("SERIES") }
    }

    private fun setupDeviceId(textView: TextView) {
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val formattedId = androidId?.uppercase()?.chunked(2)?.joinToString(":")?.take(17) ?: "00:00:00:00:00"
        textView.text = "ID: $formattedId"
    }

    private fun applyFocusScaleAnimation(view: View) {
        view.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // Scale up and bring to front
                v.animate().scaleX(1.08f).scaleY(1.08f).setDuration(200).start()
                v.z = 10f // Elevation for focus depth
            } else {
                // Scale back to normal
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start()
                v.z = 0f
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            Toast.makeText(this, "Searching for: $query", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCategory(type: String) {
        Toast.makeText(this, "Opening $type", Toast.LENGTH_SHORT).show()
    }
}