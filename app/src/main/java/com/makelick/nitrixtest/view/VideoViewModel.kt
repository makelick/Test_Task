package com.makelick.nitrixtest.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makelick.nitrixtest.data.local.model.VideoCategory
import com.makelick.nitrixtest.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    var state by mutableStateOf(VideoListState())
        private set

    init {
        handleIntent(VideoListIntent.LoadCategories)
        handleIntent(VideoListIntent.SelectCategory(null))
    }

    fun handleIntent(intent: VideoListIntent) {
        when (intent) {
            is VideoListIntent.LoadCategories -> loadCategories()
            is VideoListIntent.SelectCategory -> selectCategory(intent.category)
            else -> Unit
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            repository.getCategories().onSuccess {
                state = state.copy(categories = it, isLoading = false)
            }.onFailure {
                state = state.copy(isError = true, isLoading = false)
            }
        }
    }

    private fun selectCategory(category: VideoCategory?) {
        viewModelScope.launch {
            val selectedList = state.selectedCategories.toMutableList()
            state = state.copy(isLoading = true)

            if (category == null) {
                repository.getAllVideos().onSuccess {
                    state = state.copy(
                        videos = it,
                        selectedCategories = emptyList(),
                        isLoading = false
                    )
                }.onFailure {
                    state = state.copy(
                        isError = true,
                        isLoading = false
                    )
                }
            } else {
                if (selectedList.contains(category)) {
                    selectedList.remove(category)
                } else {
                    selectedList.add(category)
                }
                repository.getVideosByCategories(selectedList).onSuccess {
                    state = state.copy(
                        videos = it,
                        selectedCategories = selectedList,
                        isLoading = false
                    )
                }.onFailure {
                    state = state.copy(
                        isError = true,
                        isLoading = false
                    )
                }
            }
        }
    }
}