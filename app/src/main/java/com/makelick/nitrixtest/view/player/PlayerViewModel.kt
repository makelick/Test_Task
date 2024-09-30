package com.makelick.nitrixtest.view.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.makelick.nitrixtest.domain.model.UniqueMediaItem
import com.makelick.nitrixtest.domain.repositories.MediaItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: ExoPlayer,
    private val repository: MediaItemRepository
) : ViewModel() {

    private var queryList = listOf<UniqueMediaItem>()
    private val isVideoLoaded = MutableStateFlow(false)

    init {
        loadMediaItems()
    }

    private fun loadMediaItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMediaItems().onSuccess {
                queryList = it
                isVideoLoaded.value = true
            }
        }
    }

    fun playVideoWhenLoaded(id: Long) {
        if (!isVideoLoaded.value) {
            viewModelScope.launch {
                isVideoLoaded.collect {
                    if (it) {
                        playVideo(id)
                        return@collect
                    }
                }
            }
        } else {
            playVideo(id)
        }
    }

    private fun playVideo(id: Long) {
        if (player.mediaItemCount > 0) return

        val index = queryList.indexOfFirst { it.id == id }
        player.apply {
            setMediaItems(queryList.map { it.mediaItem }, index, 0)
            prepare()
            playWhenReady = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}