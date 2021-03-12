package com.example.postapp.ui.posts

import androidx.lifecycle.*
import com.example.postapp.data.local.entities.Post
import com.example.postapp.other.Event
import com.example.postapp.other.Resource
import com.example.postapp.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
        private val repository: PostRepository
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _allPosts = _forceUpdate.switchMap {
        repository.getAllPosts().asLiveData(viewModelScope.coroutineContext)
    }.switchMap {
        MutableLiveData(Event(it))
    }

    val allNotes: LiveData<Event<Resource<List<Post>>>> = _allPosts
}