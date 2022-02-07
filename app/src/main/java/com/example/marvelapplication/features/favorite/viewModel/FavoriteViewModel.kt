package com.example.marvelapplication.features.favorite.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import com.example.marvelapplication.features.favorite.database.FavoriteDatabase
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.model.*
import com.example.marvelapplication.features.favorite.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class FavoriteViewModel(application: Application): AndroidViewModel(application) {


    open val favorites: LiveData<List<FavoriteDto>?> get() =_favorites
    private val _favorites = MutableLiveData<List<FavoriteDto>?>()
    open val deleted: LiveData<Unit?> get() = _deleted
    private val _deleted = MutableLiveData<Unit?>()
    open val error: LiveData<Throwable> get() = _error
    private val _error = MutableLiveData<Throwable>()
    open val listMode: LiveData<Boolean?> get() = _listMode
    private val _listMode = MutableLiveData<Boolean?>()
    open val savedListMode: LiveData<Unit?> get() = _savedListMode
    private val _savedListMode = MutableLiveData<Unit?>()

    val repository: FavoriteRepository
    val allFav: LiveData<List<FavoriteDto>>

    init {
        val dao=FavoriteDatabase.getDatabase(application).getFavoriteDao()
        repository= FavoriteRepository(dao)
        allFav=repository.allFavorite
    }

    fun deleteFavorite(favoriteDto: FavoriteDto)=viewModelScope.launch(Dispatchers.IO){
        repository.deleteFave(favoriteDto)
    }

    fun addFavorite(favoriteDto: FavoriteDto) =viewModelScope.launch(Dispatchers.IO){
        repository.insertFav(favoriteDto)
    }
 /*   open fun getFavorites() {
        viewModelScope.launch {
            _favorites.value =allFavorite
        }
    }

    open fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            _deleted.value = deleteFavoriteUseCase(favorite)
        }
    }

    open fun saveListMode(listMode: Boolean) {
        viewModelScope.launch {
            _savedListMode.value = saveListModeUseCase(listMode)
        }
    }

    open fun getListMode() {
        viewModelScope.launch {
            _listMode.value = getListModeUseCase()
        }
    }*/
}