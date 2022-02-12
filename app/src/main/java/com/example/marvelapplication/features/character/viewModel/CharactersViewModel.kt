package com.example.marvelapplication.features.character.viewModel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.marvelapplication.features.character.model.MarvelCharacters
import com.example.marvelapplication.features.character.model.MarvelCharacterList

import com.example.marvelapplication.features.character.repository.CharacterRepository
import com.example.marvelapplication.features.favorite.database.FavoriteDao
import com.example.marvelapplication.features.favorite.database.FavoriteDto
import com.example.marvelapplication.features.favorite.repository.FavoriteRepository
import com.example.marvelapplication.features.favorite.repository.SaveFavoriteUseCase
import com.example.marvelapplication.features.favorite.viewModel.FavoriteViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.IllegalArgumentException


class CharactersViewModel: ViewModel() {


    open val characters: LiveData<List<MarvelCharacters>?> get() = _characters
    private val _characters = MutableLiveData<List<MarvelCharacters>?>()
    open val error: LiveData<Throwable> get() = _error
    private val _error = MutableLiveData<Throwable>()
    open val savedFavorite: LiveData<Long?> get() = _savedFavorite
    private val _savedFavorite = MutableLiveData<Long?>()
    open val listMode: LiveData<Boolean?> get() = _listMode
    private val _listMode = MutableLiveData<Boolean?>()
    open val savedListMode: LiveData<Unit?> get() = _savedListMode
    private val _savedListMode = MutableLiveData<Unit?>()
    private var characterRepository = CharacterRepository()

    open val charactersData: LiveData<MarvelCharacterList?> get() = _charactersData
    private val _charactersData = MutableLiveData<MarvelCharacterList?>()


    open fun getCharactersFromApi(apikey: String, timestamp: Long, hash: String, offset: Int) {

        viewModelScope.launch {

            var response: Response<MarvelCharacterList> =
                characterRepository.getCharacter(apikey, hash, timestamp, offset)
            if (response.isSuccessful) {
                _charactersData.value = response.body()
            } else {

            }
        }
    }



}
