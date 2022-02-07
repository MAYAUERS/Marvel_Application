package com.example.marvelapplication.features.character.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapplication.features.character.model.MarvelCharacters
import com.example.marvelapplication.features.character.model.MarvelCharacterList

import com.example.marvelapplication.features.character.repository.CharacterRepository

import kotlinx.coroutines.launch
import retrofit2.Response


class CharactersViewModel : ViewModel() {


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
    private var repository= CharacterRepository()

    open val charactersData: LiveData<MarvelCharacterList?> get() = _charactersData
    private val _charactersData = MutableLiveData<MarvelCharacterList?>()

    open fun getCharactersFromApi(apikey: String, timestamp: Long, hash: String, offset: Int) {

        viewModelScope.launch {

            var response:Response<MarvelCharacterList> = repository.getCharacter(apikey,hash,timestamp,offset)
            if (response.isSuccessful){
                _charactersData.value= response.body()
            }else{

            }
        }
    }
}