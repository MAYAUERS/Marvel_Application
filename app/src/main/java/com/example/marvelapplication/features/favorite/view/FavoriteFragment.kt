package com.example.marvelapplication.features.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapplication.R
import com.example.marvelapplication.databinding.FragmentFavoriteBinding
import com.example.marvelapplication.extensions.gone
import com.example.marvelapplication.extensions.visible
import com.example.marvelapplication.features.character.network.Config.refreshFavorite
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var favoriteAdapter = FavoriteAdapter()

    private var contentAsList: Boolean? = null
    private var deleteListener = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeViewModel()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {

        favoriteViewModel?.favorites?.observe(viewLifecycleOwner) {
            if (it?.size == 0) {
                showPlaceholder()
            } else {
                showList()
            }

            setList(it)
        }

        favoriteViewModel?.error?.observe(viewLifecycleOwner) {
            showPlaceholder()
        }

        favoriteViewModel?.deleted?.observe(viewLifecycleOwner) {
            if (deleteListener) {
                deleteListener = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.favorite_deleted),
                    Toast.LENGTH_SHORT
                ).show()

                favoriteViewModel?.deleted
            }
        }

        favoriteViewModel?.listMode?.observe(viewLifecycleOwner) {
            contentAsList = it ?: false

            refreshList()

            activity?.invalidateOptionsMenu()
        }

        favoriteViewModel?.savedListMode?.observe(viewLifecycleOwner) {
            favoriteViewModel?.favorites
        }
    }

    private fun showList() {
        binding.favoriteList.visible()
        binding.favoritePlaceholder.gone()
        binding.favoriteListLoader.gone()
        binding.favoriteProgressBar.gone()
    }

    private fun showPlaceholder() {
        binding.favoritePlaceholder.visible()
        binding.favoriteListLoader.gone()
        binding.favoriteProgressBar.gone()
    }

    private fun setList(favorites: List<FavoriteDto>?) {
        favoriteAdapter.clearItems()
        favoriteAdapter.setItems(favorites)

        favoriteAdapter.clickListenerFavorite= {
            favoriteViewModel?.addFavorite(it)
            contentAsList = true
        }
        favoriteAdapter.clickListenerFavorite = { favorite ->
            favoriteViewModel?.deleteFavorite(favorite)
            deleteListener = true
        }


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

    private fun refreshList() {
        if (binding.favoriteList.layoutManager == null || refreshFavorite) {
            refreshFavorite = false
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

    override fun onResume() {
        super.onResume()

        favoriteViewModel?.allFav
    }
}