package com.example.postapp.ui.postdetail

import androidx.lifecycle.ViewModel
import com.example.postapp.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
        private val repository: PostRepository
) : ViewModel() {

    fun observePostByID(postID: Int) = repository.observePostByID(postID)

}