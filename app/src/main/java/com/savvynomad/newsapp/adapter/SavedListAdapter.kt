package com.savvynomad.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.savvynomad.newsapp.databinding.NewsListItemBinding
import com.savvynomad.newsapp.model.Article

class SavedListAdapter(val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SavedListAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = listArticle[position]
                    onItemClickListener.onItemClick(item)
                }

            }
        }

        fun bindItem(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .into(ivArticle)

                tvTitle.text = article.title
                tvDesc.text = article.description

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = listArticle[position]

        holder.bindItem(currentItem)
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)
    var listArticle: List<Article>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

}