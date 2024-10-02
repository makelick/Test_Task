package com.makelick.nitrixtest.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    var state by mutableStateOf(VideoListState())
        private set

    init {
        handleIntent(VideoListIntent.LoadData)
    }

    fun handleIntent(intent: VideoListIntent) {
        when (intent) {
            is VideoListIntent.LoadData -> {
                loadRemoteData()
            }

            is VideoListIntent.ClearError -> {
                state = state.copy(isError = false)
            }

            is VideoListIntent.SelectCategory -> {
                selectCategory(intent.category)
                loadVideos()
            }

            else -> Unit
        }
    }

    private fun loadRemoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true, isError = false)

            repository.fetchRemoteData()
                .onSuccess {
                    state = state.copy(isLoading = false)
                }
                .onFailure {
                    state = state.copy(isError = true, isLoading = false)
                }


            loadCategories()
            loadVideos()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories()
                .onSuccess {
                    state = state.copy(categories = it)
                }
                .onFailure {
                    state = state.copy(isError = true)
                }
        }
    }

    private fun loadVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)

            repository.getVideos(state.selectedCategories)
                .onSuccess {
                    state = state.copy(videos = it, isLoading = false)
                }
                .onFailure {
                    state = state.copy(isError = true, isLoading = false)
                }
        }
    }

    private fun selectCategory(category: VideoCategory?) {
        val selectedList =
            if (category == null) mutableListOf()
            else state.selectedCategories.toMutableList()

        if (selectedList.contains(category)) {
            selectedList.remove(category)
        } else {
            category?.let {
                selectedList.add(category)
            }
        }

        state = state.copy(selectedCategories = selectedList)
    }
}