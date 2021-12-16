package com.savvynomad.newsapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.savvynomad.newsapp.R
import com.savvynomad.newsapp.databinding.FragmentDetailBinding
import com.savvynomad.newsapp.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: NewsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        val article = args.article
        binding.apply {
            tvTitle.text = article.title
            val s = article.content ?: ""
            tvContent.text = Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT)
            ivArticle.load(article.urlToImage) {
                crossfade(true)
                error(R.drawable.ic_broken_image)
                placeholder(R.drawable.ic_article_default)

            }
            btnRead.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri = Uri.parse(article.url)
                intent.data = uri
                startActivity(intent)

            }

            floatingActionButton.setOnClickListener {
                article.isBookmarked = true

                Snackbar.make(
                    detailLayout, "Added to bookmark", Snackbar.LENGTH_SHORT
                ).show()
            }

            ivArticle.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvContent.visibility = View.VISIBLE
            btnRead.visibility = View.VISIBLE
            floatingActionButton.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}