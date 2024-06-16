package com.rempahpedia.rempahpedia.ui.rempah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rempahpedia.rempahpedia.data.remote.api.ApiConfig
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahDetailResponse
import com.rempahpedia.rempahpedia.data.remote.rempah.RempahResponseItem
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RempahViewModel : ViewModel() {
    private val _listRempah = MutableLiveData<List<RempahResponseItem>>()
    val listRempah: LiveData<List<RempahResponseItem>> = _listRempah

    private val _rempahDetail = MutableLiveData<RempahDetailResponse>()
    val rempahDetail: LiveData<RempahDetailResponse> = _rempahDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getAllRempah(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rempahs = ApiConfig.getApiService().getAllRempah(token)
                _listRempah.value = rempahs
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                _errorMessage.value = "Error saat mengambil data"
            }
        }
    }

    fun getRempahById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rempahDetail = ApiConfig.getApiService().getRempahById(id)
                _rempahDetail.value = rempahDetail
                _isLoading.value = false
            } catch (e: HttpException) {
                _isLoading.value = false
                _errorMessage.value = "Error saat mengambil data"
            }
        }
    }
}