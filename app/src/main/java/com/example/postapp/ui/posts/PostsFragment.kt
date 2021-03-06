package com.example.postapp.ui.posts

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.R
import com.example.postapp.adapters.PostAdapter
import com.example.postapp.other.Status
import com.example.postapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : BaseFragment(R.layout.fragment_posts) {

    private val viewModel: PostViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPreferences

    private lateinit var postAdapter: PostAdapter

    private val swipingItem = MutableLiveData(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        setupRecyclerView()
        setupSwipeRefreshLayout()
        subscribeToObservers()
        postAdapter.setOnItemClickListener {
            findNavController().navigate(
                    PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(it.id,it.userId)
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.allPosts.observe(viewLifecycleOwner, Observer {
            it?.let { event ->
                val result = event.peekContent()
                when(result.status){
                    Status.SUCCESS -> {
                        postAdapter.posts = result.data!!
                        swipeRefreshLayout.isRefreshing = false
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

                        swipeRefreshLayout.isRefreshing = false
                    }

                    Status.LOADING -> {
                        result.data?.let { posts ->
                            postAdapter.posts = posts
                        }

                        swipeRefreshLayout.isRefreshing = true
                    }
                }

            }
        })

        swipingItem.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isEnabled = !it
        })
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.syncAllPosts()
        }
    }

    private fun  setupRecyclerView() = rvPosts.apply {
        postAdapter = PostAdapter()
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())

    }


}