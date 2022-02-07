package com.example.marvelapplication.features.favorite.viewModel


import androidx.lifecycle.*
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.repository.FavoriteRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


open class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {

    fun addFavMarvel(favoriteDto: FavoriteDto) {
        viewModelScope.launch {
           // repository.insertFavMarvel(favoriteDto)
        }
    }

    val allMarvelFavList:LiveData<List<FavoriteDto>> =repository.allMarvelList.asLiveData()

}

class FavViewModelFactory(private val repository: FavoriteRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){

            return FavoriteViewModel(repository) as  T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}