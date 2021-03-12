package com.example.postapp.ui.posts

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postapp.R
import com.example.postapp.adapters.PostAdapter
import com.example.postapp.other.Status
import com.example.postapp.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment : BaseFragment(R.layout.fragment_posts) {

    private val viewModel: PostViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPreferences

    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        setupRecyclerView()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            it?.let { event ->
                val result = event.peekContent()
                when(result.status){
                    Status.SUCCESS -> {
                        postAdapter.posts = result.data!!
                    }
                    Status.ERROR -> {
                        event.getContentIfNotHandled()?.let { errorResource ->
                            errorResource.message?.let { message ->
                                showSnackbar(message)
                            }
                        }

                        result.data?.let { posts ->
                            postAdapter.posts = posts
                        }
                    }

                    Status.LOADING -> {
                        result.data?.let { posts ->
                            postAdapter.posts = posts
                        }
                    }
                }

            }
        })
    }

    private fun  setupRecyclerView() = rvPosts.apply {
        postAdapter = PostAdapter()
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())

    }


}