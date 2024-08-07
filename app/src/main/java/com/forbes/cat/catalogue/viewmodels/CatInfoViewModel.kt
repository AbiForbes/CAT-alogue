package com.forbes.cat.catalogue.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forbes.cat.catalogue.api.BreedApi
import com.forbes.cat.catalogue.responses.IdResponse
import kotlinx.coroutines.launch

class CatInfoViewModel: ViewModel() {
        private val _idList = MutableLiveData<IdResponse>()
        var errorMessage: String by mutableStateOf("")
        val idList: IdResponse?
            get() = _idList.value

        fun getCatInfo(id: String) {
            viewModelScope.launch {
                try {
                    _idList.value = null
                    Log.d("CatInfoViewModel", idList.toString())
                    _idList.value = BreedApi.breedService.getCatInfo(id)
                    val list = BreedApi.breedService.getCatInfo(id)

                    Log.d("CatInfoViewModel2", list.toString())

                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                    Log.e("CatInfoViewModel", errorMessage)
                }
            }
        }
}