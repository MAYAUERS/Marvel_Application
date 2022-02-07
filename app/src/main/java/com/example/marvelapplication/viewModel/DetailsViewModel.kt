package com.example.marvelapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapplication.features.favorite.model.Favorite
import com.example.marvelapplication.features.favorite.repository.FavoriteRepository
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


}