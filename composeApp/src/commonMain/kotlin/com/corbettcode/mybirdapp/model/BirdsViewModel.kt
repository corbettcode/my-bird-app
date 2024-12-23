package com.corbettcode.mybirdapp.model

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BirdsUiState(
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = images.filter { it.category == selectedCategory }
}

class BirdsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<BirdsUiState>(BirdsUiState())
    val uiState: StateFlow<BirdsUiState> = _uiState.asStateFlow()

    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        updateImages()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            val images: List<BirdImage> = getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }

    private suspend fun getImages(): List<BirdImage> {
        val images: List<BirdImage> = httpClient
            .get( "https://corbettcode.github.io/demo-data-api/pictures.json")
            .body<List<BirdImage>>()
        return images
    }
}