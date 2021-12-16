package com.savvynomad.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.savvynomad.newsapp.R
import com.savvynomad.newsapp.adapter.NewsPagingAdapter
import com.savvynomad.newsapp.databinding.FragmentHomeBinding
import com.savvynomad.newsapp.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), NewsPagingAdapter.OnItemClickListener {
    private val viewModel: NewsViewModel by viewModels()


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsPagingAdapter

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        adapter = NewsPagingAdapter(this@HomeFragment)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE
        }

        viewModel.news.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchArticles(query)
                    searchView.clearFocus()
                    binding.recyclerView.smoothScrollToPosition(0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.searchArticles("")
                return true
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(article: Article) {
        val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail2(article)
        findNavController().navigate(action)
    }

}