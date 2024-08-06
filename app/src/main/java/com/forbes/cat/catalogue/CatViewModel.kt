package com.forbes.cat.catalogue

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatViewModel: ViewModel() {
        private val _imageList = mutableStateListOf<ImageResponse>()
        private var _hasBeenCalled = false
        var errorMessage: String by mutableStateOf("")
        val imageList: List<ImageResponse>
            get() = _imageList
        var catListComplete: Boolean
            get() = _hasBeenCalled
            set(hasBeenCalled) {_hasBeenCalled = hasBeenCalled
            getCatImageList()}

        fun getCatImageList() {
            viewModelScope.launch {
                val apiService = BreedApi
                if(!_hasBeenCalled){
                    try {
                        Log.d("CatViewModel", imageList.toString())
                        _imageList.addAll(apiService.breedService.getImages("live_wB12GWcB6KarWdktFbIuJqnslDelGZPf3lkd9p5MX1tgd8WM4EWTxN94z2OuH4hR", 1, 10))
                        val list = apiService.breedService.getImages("live_wB12GWcB6KarWdktFbIuJqnslDelGZPf3lkd9p5MX1tgd8WM4EWTxN94z2OuH4hR", 1, 10)
                        Log.d("CatViewModel2", list.toString())
                        _hasBeenCalled = true

                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                        Log.e("CatViewModel", errorMessage)
                    }
                }
            }
        }
}