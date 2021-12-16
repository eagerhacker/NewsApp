package com.savvynomad.newsapp.ui.save

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.savvynomad.newsapp.R
import com.savvynomad.newsapp.adapter.SavedListAdapter
import com.savvynomad.newsapp.databinding.FragmentSavedBinding
import com.savvynomad.newsapp.model.Article
import com.savvynomad.newsapp.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved), SavedListAdapter.OnItemClickListener {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var adapter: SavedListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)

        adapter = SavedListAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

//            progressBar.visibility = View.VISIBLE
        }

        /*   val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
               override fun onMove(
                   recyclerView: RecyclerView,
                   viewHolder: RecyclerView.ViewHolder,
                   target: RecyclerView.ViewHolder
               ): Boolean {
                   return false
               }

               override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                   val position = viewHolder.bindingAdapterPosition
                   val article = adapter.listArticle[position]
   //                viewModel.deleteArticle(article)
                   Snackbar.make(view, "Removed", Snackbar.LENGTH_SHORT).apply {
                       setAction("Undo") {
   //                        viewModel.saveArticle(article)
                       }
                       show()
                   }
               }

           }

           ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView) */
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(article: Article) {
        val action = SavedFragmentDirections.actionFragmentSavedToFragmentDetail2(article)
        findNavController().navigate(action)
    }


}