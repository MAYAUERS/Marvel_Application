package com.example.marvelapplication.features.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapplication.R
import com.example.marvelapplication.application.MarvelApplication
import com.example.marvelapplication.databinding.FragmentFavoriteBinding
import com.example.marvelapplication.extensions.gone
import com.example.marvelapplication.extensions.visible
import com.example.marvelapplication.features.character.model.MarvelCharacters
import com.example.marvelapplication.features.character.network.Config.refreshFavorite
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.viewModel.FavViewModelFactory
import com.example.marvelapplication.features.favorite.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment() {

   // private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isLoading = false
    private var offset = 0
    private var favoriteAdapter = FavoriteAdapter()

    private var contentAsList: Boolean? = null
    private var deleteListener = false
    private var isFirstPage = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

           // ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()
        return root
    }
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavViewModelFactory((requireActivity().application as MarvelApplication).repository)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
       /* favoriteViewModel?.allMarvelFavList?.observe(viewLifecycleOwner) {
            if (it?.size == 0) {
                showPlaceholder()
            } else {
                showList()
            }
            setList(it)
        }*/
    }

    private fun showList() {
        binding.favoriteList.visible()
        binding.favoritePlaceholder.gone()
        binding.favoriteListLoader.gone()
        binding.favoriteProgressBar.gone()
    }

    private fun showPlaceholder() {
        if (!binding.favoriteList.isVisible ) {
            binding.favoritePlaceholder.visible()
            binding.favoriteListLoader.gone()
            binding.favoriteProgressBar.gone()
            binding.favoriteList.gone()
        } else {
            binding.favoriteListLoader.gone()
        }
    }
    private fun setList(favorite: List<FavoriteDto>?) {
        favoriteAdapter.clearItems()
        favoriteAdapter.setItems(favorite)

        if (binding.favoriteList.layoutManager == null) {
            binding.favoriteList.apply {
                setHasFixedSize(true)

                layoutManager = if (contentAsList == true) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                adapter = favoriteAdapter
            }
        }

    }
}