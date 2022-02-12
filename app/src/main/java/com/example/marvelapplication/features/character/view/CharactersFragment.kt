package com.example.marvelapplication.features.character.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.marvelapplication.R

import com.example.marvelapplication.features.character.model.MarvelCharacters
import com.example.marvelapplication.features.character.network.Config
import com.example.marvelapplication.features.character.network.Config.API_KEY
import com.example.marvelapplication.features.character.network.Config.refreshCharacter
import com.example.marvelapplication.databinding.FragmentCharactersBinding
import com.example.marvelapplication.features.character.viewModel.CharactersViewModel
import com.example.marvelapplication.extensions.gone
import com.example.marvelapplication.extensions.md5
import com.example.marvelapplication.extensions.visible
import com.example.marvelapplication.features.character.network.Config.refreshFavorite
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.view.activity.MainActivity

import java.util.*

class CharactersFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var charactersViewModel: CharactersViewModel
    private var _binding: FragmentCharactersBinding? = null
    private var characterAdapter = CharacterAdapter()
    private var isFirstPage = true
    private var offset = 0
    private var contentAsList: Boolean? = null
    private var favoriteListener = false
    private val main: MainActivity get() = (activity as MainActivity)
    private val navController: NavController? get() = main.navController

    private val binding get() = _binding!!
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charactersViewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeViewModel()
        setLayout()
        setScreen()
        return root
    }

    private fun setLayout() {
        binding.charactersRefresh.setOnRefreshListener(this)

        binding.charactersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true

                    charactersViewModel.getCharactersFromApi(API_KEY, createTimestamp(), createHash(createTimestamp()), offset )

                    binding.charactersListLoader.visible()
                    binding.charactersList.scrollToPosition(characterAdapter.itemCount - 1)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        charactersViewModel.getCharactersFromApi(API_KEY, createTimestamp(), createHash(createTimestamp()), offset )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh() {
        offset=0
        characterAdapter.clearItems()
        charactersViewModel.getCharactersFromApi(API_KEY, createTimestamp(), createHash(createTimestamp()), offset )
    }

    private fun observeViewModel() {
        charactersViewModel.charactersData.observe(viewLifecycleOwner){character->
            var characters= character?.data?.results

            if (characters?.isNotEmpty() == true) {
                binding.charactersRefresh.isRefreshing = false

                offset += characters.size

                isLoading = false

                showList()

                setList(characters)
            } else {
                showPlaceholder()
            }
        }

        charactersViewModel?.error?.observe(viewLifecycleOwner) {
            binding.charactersRefresh.isRefreshing = false

            if (isLoading) {
                isLoading = false
            }

            showPlaceholder()
        }

        charactersViewModel?.savedFavorite?.observe(viewLifecycleOwner) {
            if (favoriteListener) {
                favoriteListener = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.characters_added_favorite),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        charactersViewModel?.listMode?.observe(viewLifecycleOwner) {
            contentAsList = it ?: false

            refreshList()

            activity?.invalidateOptionsMenu()
        }
    }

    private fun setList(characters: List<MarvelCharacters>?) {
        characterAdapter.setItems(characters)

        characterAdapter.clickListener = { characterId ->
            val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment()
            action.characterId = characterId
            navController?.navigate(action)
        }
        if (isFirstPage && binding.charactersList.layoutManager == null) {
            isFirstPage = false

            binding.charactersList.apply {
                setHasFixedSize(true)

                layoutManager = if (contentAsList == true) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                adapter = characterAdapter
            }
        }



    }
    private fun refreshList() {
        if (binding.charactersList.layoutManager == null || refreshCharacter) {
            refreshCharacter = false
            binding.charactersList.apply {
                setHasFixedSize(true)

                layoutManager = if (contentAsList == true) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                adapter = characterAdapter
            }
        }
    }

    private fun showList() {
        binding.charactersList.visible()
        binding.charactersPlaceholder.gone()
        binding.charactersListLoader.gone()
        binding.charactersProgressBar.gone()
    }

    private fun showPlaceholder() {
        if (!binding.charactersList.isVisible || isFirstPage) {
            binding.charactersPlaceholder.visible()
            binding.charactersListLoader.gone()
            binding.charactersProgressBar.gone()
            binding.charactersList.gone()
        } else {
            binding.charactersListLoader.gone()
        }
    }

    fun createTimestamp() = Date().time
    fun createHash(timestamp: Long) = (timestamp.toString() + Config.PRIVATE_KEY + API_KEY).md5()


   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_character_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }*/
    private fun setScreen() {
        hideBackArrow()
        setHasOptionsMenu(true)
    }

    fun hideBackArrow() {
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }



}