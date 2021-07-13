package com.savvynomad.newsapp.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.savvynomad.newsapp.R
import com.savvynomad.newsapp.databinding.NewsListItemBinding
import com.savvynomad.newsapp.model.Article

class NewsPagingAdapter(val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<Article, NewsPagingAdapter.NewsViewHolder>(diffCallback) {
    inner class NewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if(item != null)
                        onItemClickListener.onItemClick(item)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun bindItem(currentItem: Article) {
            binding.apply {
                tvTitle.text = currentItem.title
                val desc = currentItem.description ?: ""
                tvDesc.text = Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT)
                Glide.with(itemView)
                    .load(currentItem.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_image)
                    .into(ivArticle)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bindItem(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }


        }
    }
}