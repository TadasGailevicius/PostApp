package com.example.postapp.ui.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.postapp.R
import com.example.postapp.data.local.entities.Post
import com.example.postapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_post_detail.*

@AndroidEntryPoint
class PostDetailFragment : BaseFragment(R.layout.fragment_post_detail)  {

    private val viewModel: PostDetailViewModel by viewModels()

    private val args: PostDetailFragmentArgs by navArgs()

    private var curPost: Post? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.observePostByID(args.id).observe(viewLifecycleOwner, Observer {
            it?.let { post ->
                tvPostTitle.text = post.title
                tvPostBody.text = post.body
                curPost = post
            } ?: showSnackbar("Post not found")
        })
    }
}