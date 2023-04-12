package com.rajit.dynamicbackground.adapter

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rajit.dynamicbackground.R
import com.rajit.dynamicbackground.data.model.ResultSingleItem
import com.rajit.dynamicbackground.databinding.MainItemBinding
import com.rajit.dynamicbackground.util.linearGradientBackground

class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerAdapter.ArtistViewHolder>() {

    // Diff Callback for better optimization of Recyclerview Items
    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<ResultSingleItem>(){
            override fun areItemsTheSame(oldItem: ResultSingleItem, newItem: ResultSingleItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ResultSingleItem, newItem: ResultSingleItem): Boolean {
                return oldItem == newItem
            }

        }
    }


    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<ResultSingleItem>) = differ.submitList(list)

    inner class ArtistViewHolder(
        private val binding: MainItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        var dominantColor: Int = 0

        fun bind(item: ResultSingleItem) {

            binding.apply {
                artistId.text = item.name

                // Load image from url into imageview with custom Gradient Drawable
                Glide.with(root)
                    .load(item.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            val drawable = resource as BitmapDrawable
                            val bitmap = drawable.bitmap

                            // using Palette library to extract the dominant color
                            Palette.Builder(bitmap).generate {
                                it?.let {palette ->
                                    dominantColor = palette.getDominantColor(
                                        ContextCompat.getColor(
                                            root.context,
                                            R.color.white
                                        )
                                    )

                                    // setting dominant color to the gradient drawable
                                    // Here, linearGradientBackground is a custom extension function
                                    rootLayout.background = rootLayout.linearGradientBackground(dominantColor)

                                }
                            }

                            return false

                        }

                    }).into(artistImage)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            MainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = differ.currentList[position]
        holder.bind(artist)
    }

}