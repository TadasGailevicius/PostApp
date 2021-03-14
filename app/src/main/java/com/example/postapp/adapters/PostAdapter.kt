package com.example.postapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.R
import com.example.postapp.data.local.entities.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private var onItemClickListener: ((Post) -> Unit)? = null

    private val differ = AsyncListDiffer(this, diffCallback)

    var posts: List<Post>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_post,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.itemView.apply {
            tvTitle.text = post.title

            setOnItemClickListener {
                onItemClickListener?.let { click ->
                    click(post)
                }
            }
        }
    }

    fun setOnItemClickListener(onItemClick: (Post) -> Unit){
        this.onItemClickListener = onItemClick
    }
}