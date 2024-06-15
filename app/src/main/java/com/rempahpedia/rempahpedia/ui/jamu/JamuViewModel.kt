package com.rempahpedia.rempahpedia.ui.jamu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JamuViewModel : ViewModel() {
    private val _listJamu = MutableLiveData<List<JamuResponseItem>>()
    val listJamu: LiveData<List<JamuResponseItem>> = _listJamu

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getAllJamu()
    }

    private fun getAllJamu() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val jamus = ApiConfig.getApiService().getJamu()
                _listJamu.value = jamus
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false

//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, StoryResponse::class.java)
//                val errorMessage = errorBody.message
//                _errorMessage.value = errorMessage.toString()
            }
        }
    }
}