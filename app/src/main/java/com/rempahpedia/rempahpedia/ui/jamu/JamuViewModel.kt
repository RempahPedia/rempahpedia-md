package com.rempahpedia.rempahpedia.ui.jamu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuDetailResponse
import com.rempahpedia.rempahpedia.data.remote.jamu.JamuResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JamuViewModel : ViewModel() {
    private val _listJamu = MutableLiveData<List<JamuResponseItem>>()
    val listJamu: LiveData<List<JamuResponseItem>> = _listJamu

    private val _jamuDetail = MutableLiveData<JamuDetailResponse>()
    val jamuDetail: LiveData<JamuDetailResponse> = _jamuDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getAllJamu() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val jamus = ApiConfig.getApiService().getAllJamu()
                _listJamu.value = jamus
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                _errorMessage.value = "Error saat mengambil data"
            }
        }
    }

    fun getJamuById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val jamuDetail = ApiConfig.getApiService().getJamuById(id)
                _jamuDetail.value = jamuDetail
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                _errorMessage.value = "Error saat mengambil data"
            }
        }
    }

    fun searchJamu(keyword: String, filter: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {

                val jamus = ApiConfig
                    .getApiService()
                    .searchJamu(keyword, filter)

                _listJamu.value = jamus
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                _errorMessage.value = "Error saat mengambil data"
            }
        }
    }
}