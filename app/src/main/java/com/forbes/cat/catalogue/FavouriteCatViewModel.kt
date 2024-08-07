package com.forbes.cat.catalogue

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavouriteCatViewModel: ViewModel() {
    private val _imageResponse = MutableLiveData<ImageResponse>()
    var errorMessage: String by mutableStateOf("")
    val imageResponse: ImageResponse?
        get() = _imageResponse.value

    fun getFavCatInfo(breed: String) {
        viewModelScope.launch {
            val apiService = BreedApi
            try {
                _imageResponse.value = null
                Log.d("CatInfoViewModel", imageResponse.toString())
                _imageResponse.value = apiService.breedService.getFavouriteCatExample("live_wB12GWcB6KarWdktFbIuJqnslDelGZPf3lkd9p5MX1tgd8WM4EWTxN94z2OuH4hR", breed, 1)[0]
                val list = apiService.breedService.getFavouriteCatExample("live_wB12GWcB6KarWdktFbIuJqnslDelGZPf3lkd9p5MX1tgd8WM4EWTxN94z2OuH4hR", breed, 1)[0]

                Log.d("CatInfoViewModel2", list.toString())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("CatInfoViewModel", errorMessage)
            }
        }
    }
}