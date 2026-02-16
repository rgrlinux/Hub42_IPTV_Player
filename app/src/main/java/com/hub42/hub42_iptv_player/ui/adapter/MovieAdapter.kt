package com.hub42.hub42_iptv_player.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hub42.hub42_iptv_player.R
import com.hub42.hub42_iptv_player.model.Channel

class MovieAdapter(
    private val movies: List<Channel>,
    private val onClick: (Channel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster: ImageView = view.findViewById(R.id.imgPoster)
        val txtTitle: TextView = view.findViewById(R.id.txtMovieTitle)

        init {
            // Apply Focus Animation
            view.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start()
                    v.elevation = 10f
                } else {
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).start()
                    v.elevation = 0f
                }
            }
            view.setOnClickListener { onClick(movies[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.txtTitle.text = movie.name

        Glide.with(holder.itemView.context)
            .load(movie.logoUrl)
            .placeholder(R.drawable.border_cyan) // Your cyan border as placeholder
            .into(holder.imgPoster)
    }

    override fun getItemCount() = movies.size
}