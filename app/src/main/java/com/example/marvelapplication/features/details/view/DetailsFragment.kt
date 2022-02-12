package com.example.marvelapplication.features.details.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvelapplication.R
import com.example.marvelapplication.databinding.FragmentDetailsBinding
import com.example.marvelapplication.extensions.gone
import com.example.marvelapplication.extensions.md5
import com.example.marvelapplication.extensions.visible
import com.example.marvelapplication.features.character.network.Config.API_KEY
import com.example.marvelapplication.features.character.network.Config.PRIVATE_KEY
import com.example.marvelapplication.features.details.model.CharacterDetail
import com.example.marvelapplication.features.details.model.DetailInfo
import com.example.marvelapplication.features.details.model.Thumbnail
import com.example.marvelapplication.features.details.viewModel.DetailsViewModel
import com.example.marvelapplication.view.activity.MainActivity
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var isFirstPageComics = true
    private var isFirstPageSeries = true
    private var offsetComics = 0
    private var offsetSeries = 0
    private var isLoadingComics = false
    private var isLoadingSeries = false
    private var comicsAdapter = DetailsInfoAdapter()
    private var seriesAdapter = DetailsInfoAdapter()

    private var characterId: Long = 0L
    private val main: MainActivity get() = (activity as MainActivity)
    val navController: NavController? get() = main.navController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterId = arguments?.let {
            DetailsFragmentArgs.fromBundle(it).characterId } ?: 0L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setScreen()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()

        detailsViewModel?.getMarvelCharacterDetails(
            characterId,
            API_KEY,
            createTimestamp(),
            createHash(createTimestamp())
        )
        detailsViewModel?.getMarvelComicDetails(
            characterId,
            API_KEY,
            createTimestamp(),
            createHash(createTimestamp()),
            offsetComics
        )
        detailsViewModel?.getMarvelSeriesDetails(
            characterId,
            API_KEY,
            createTimestamp(),
            createHash(createTimestamp()),
            offsetSeries
        )


        observeViewModel()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLayout() {


        binding.detailsCharacterComicsList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollHorizontally(1) && !isLoadingComics) {
                    isLoadingComics = true

                    detailsViewModel?.getMarvelComicDetails(
                        characterId,
                        API_KEY,
                        createTimestamp(),
                        createHash(createTimestamp()),
                        offsetComics
                    )
                    binding.detailsCharacterComicsListLoader.visible()
                    binding.detailsCharacterComicsList.scrollToPosition(comicsAdapter.itemCount - 1)
                }
            }
        })

        binding.detailsCharacterSeriesList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollHorizontally(1) && !isLoadingSeries) {
                    isLoadingSeries = true

                    detailsViewModel?.getMarvelSeriesDetails(
                        characterId,
                        API_KEY,
                        createTimestamp(),
                        createHash(createTimestamp()),
                        offsetSeries
                    )

                    binding.detailsCharacterSeriesListLoader.visible()
                    binding.detailsCharacterSeriesList.scrollToPosition(seriesAdapter.itemCount - 1)
                }
            }
        })
    }

    private fun observeViewModel() {

        detailsViewModel?.detailsData?.observe(viewLifecycleOwner) {
            var characterDetail: List<CharacterDetail>? =it?.data?.results
            characterDetail?.get(0)?.description
            characterDetail?.get(0)?.thumbnail
            Log.i("CHARACTER_DETAIL"," "+ characterDetail?.get(0)?.description.toString() +"\n"
                    + characterDetail?.get(0)?.thumbnail.toString() +"\n"
                    + characterDetail?.get(0)?.name.toString() +"\n"
            )
            binding.detailsCharacterDescription.text = if (characterDetail?.get(0)?.description?.isNotEmpty() == true) {
                characterDetail?.get(0)?.description.toString()
            } else {
                getString(R.string.details_no_description)
            }
            binding.detailsCharacterImage.load(characterDetail?.get(0)?.thumbnail?.getUrl(Thumbnail.ImageType.LANDSCAPE))
        }

        detailsViewModel?.comicDetails?.observe(viewLifecycleOwner) {
            var comic= it?.data?.results
            if (comic?.size ?: 0 > 0) {
                binding.detailsCharacterComicsListLoader.gone()
                isLoadingComics = false

                offsetComics += comic?.size ?: 0

                binding.detailsCharacterComics.visible()
                setComics(comic)
            } else {
                binding.detailsCharacterComicsListLoader.gone()
            }
        }

        detailsViewModel?.seriesDetails?.observe(viewLifecycleOwner) {
            var series= it?.data?.results
            if (series?.size ?: 0 > 0) {
                binding.detailsCharacterSeriesListLoader.gone()
                isLoadingSeries = false

                offsetSeries += series?.size ?: 0

                binding.detailsCharacterSeries.visible()
                setSeries(series)
            } else {
                binding.detailsCharacterSeriesListLoader.gone()
            }
        }

        detailsViewModel?.error?.observe(viewLifecycleOwner) {
            showPlaceholder()
        }

        detailsViewModel?.errorComics?.observe(viewLifecycleOwner) {
            showComicsPlaceholder()
        }

        detailsViewModel?.errorSeries?.observe(viewLifecycleOwner) {
            showSeriesPlaceholder()
        }
    }

    private fun setComics(comics: List<DetailInfo>?) {
        comicsAdapter.setItems(comics)

        if (isFirstPageComics) {
            isFirstPageComics = false

            binding.detailsCharacterComicsList.apply {
                setHasFixedSize(true)

                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                adapter = comicsAdapter
            }
        }
    }

    private fun setSeries(series: List<DetailInfo>?) {
        seriesAdapter.setItems(series)

        if (isFirstPageSeries) {
            isFirstPageSeries = false

            binding.detailsCharacterSeriesList.apply {
                setHasFixedSize(true)

                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                adapter = seriesAdapter
            }
        }
    }

    private fun showPlaceholder() {
        binding.detailsPlaceholder.visible()
    }

    private fun showComicsPlaceholder() {
        if (!binding.detailsCharacterComicsList.isVisible) {
            binding.detailsCharacterComics.visible()
            binding.detailsComicsPlaceholder.visible()
        } else {
            binding.detailsCharacterComicsListLoader.gone()
        }
    }

    private fun showSeriesPlaceholder() {
        if (!binding.detailsCharacterSeriesList.isVisible) {
            binding.detailsCharacterSeries.visible()
            binding.detailsSeriesPlaceholder.visible()
        } else {
            binding.detailsCharacterSeriesListLoader.gone()
        }
    }

    fun createTimestamp() = Date().time
    fun createHash(timestamp: Long) = (timestamp.toString() + PRIVATE_KEY + API_KEY).md5()

    private fun setScreen() {
        setTitle(getString(R.string.title_Details))
        showBackArrow()
        setHasOptionsMenu(false)
    }


    fun showBackArrow() {
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }

    fun setTitle(title: String?) {
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.title = title ?: ""
        }
    }


}