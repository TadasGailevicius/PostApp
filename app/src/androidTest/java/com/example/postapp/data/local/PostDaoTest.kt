package com.example.postapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.postapp.data.local.entities.Post
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    private lateinit var database: PostsDatabase
    private lateinit var dao: PostDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PostsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.postDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertPost() = runBlockingTest {
        val post = Post(1,1,"a","a")
        dao.insertPost(post)

        val observedPost = dao.observePostById(1).value

        assertThat(observedPost).equals(post)
    }

    @Test
    fun getAllPosts() = runBlockingTest {
        val post1 = Post(1,1,"a","a")
        val post2 = Post(2,2,"bb","bb")
        dao.insertPost(post1)
        dao.insertPost(post2)

        val liveData = dao.getAllPosts()
        val testingList: List<Post> = listOf(post1,post2)
        assertThat(liveData.equals(testingList))
    }


}