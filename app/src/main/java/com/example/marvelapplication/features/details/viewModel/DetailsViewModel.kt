package com.example.marvelapplication.features.details.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapplication.features.details.model.CharacterDetail
import com.example.marvelapplication.features.details.model.DetailInfo
import com.example.marvelapplication.features.details.model.MarvelCharacterDetail
import com.example.marvelapplication.features.details.model.MarvelDetailInfo
import com.example.marvelapplication.features.details.repository.DetailRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsViewModel : ViewModel() {

    open val details: LiveData<CharacterDetail?> get() = _details
    private val _details = MutableLiveData<CharacterDetail?>()
    open val comics: LiveData<List<DetailInfo>?> get() = _comics
    private val _comics = MutableLiveData<List<DetailInfo>?>()
    open val series: LiveData<List<DetailInfo>?> get() = _series
    private val _series = MutableLiveData<List<DetailInfo>?>()
    open val error: LiveData<Throwable> get() = _error
    private val _error = MutableLiveData<Throwable>()
    open val errorComics: LiveData<Throwable> get() = _errorComics
    private val _errorComics = MutableLiveData<Throwable>()
    open val errorSeries: LiveData<Throwable> get() = _errorSeries
    private val _errorSeries = MutableLiveData<Throwable>()
    open val savedFavorite: LiveData<Long?> get() = _savedFavorite
    private val _savedFavorite = MutableLiveData<Long?>()

    private var detailRepository= DetailRepository()
    open val detailsData: LiveData<MarvelCharacterDetail?> get() = _detailsData
    private val _detailsData = MutableLiveData<MarvelCharacterDetail?>()

    open val comicDetails: LiveData<MarvelDetailInfo?> get() = _comicDetails
    private val _comicDetails = MutableLiveData<MarvelDetailInfo?>()

    open val seriesDetails: LiveData<MarvelDetailInfo?> get() = _seriesDetails
    private val _seriesDetails = MutableLiveData<MarvelDetailInfo?>()

    private val errorMsg = MutableLiveData<String?>()


    open fun getMarvelCharacterDetails(characterId: Long, apikey: String, timestamp: Long, hash: String) {
        viewModelScope.launch {
           var response : Response<MarvelCharacterDetail> = detailRepository.getMarvelCharacterDetail(characterId,apikey,hash,timestamp)
            if (response.isSuccessful){
                _detailsData.value=response.body()
            }else{
                errorMsg.value = response.errorBody().toString()
            }
        }
    }

    open fun getMarvelDetails(characterId: Long, apikey: String, timestamp: Long, hash: String) {
        viewModelScope.launch {
            var response : Response<CharacterDetail> = detailRepository.getCharacterDetail(characterId,apikey,hash,timestamp)

            if (response.isSuccessful){
                _details.value=response.body()
                Log.i("getCharacterDetail API",response.body().toString())
            }else{
                errorMsg.value = response.errorBody().toString()
            }
        }
    }


    open fun getMarvelComicDetails(characterId: Long, apikey: String, timestamp: Long, hash: String,offset :Int) {
        viewModelScope.launch {
            var response : Response<MarvelDetailInfo> = detailRepository.getDetailsComics(characterId,apikey,hash,timestamp,offset)

            if (response.isSuccessful){
                _comicDetails.value=response.body()
            }else{
                errorMsg.value = response.errorBody().toString()
            }
        }
    }

    open fun getMarvelSeriesDetails(characterId: Long, apikey: String, timestamp: Long, hash: String,offset: Int) {
        viewModelScope.launch {
            var response : Response<MarvelDetailInfo> = detailRepository.getDetailsSeries(characterId,apikey,hash,timestamp, offset)

            if (response.isSuccessful){
                _seriesDetails.value=response.body()
            }else{
                errorMsg.value = response.errorBody().toString()
            }
        }
    }



}