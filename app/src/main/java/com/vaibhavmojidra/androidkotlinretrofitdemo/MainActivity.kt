package com.vaibhavmojidra.androidkotlinretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavmojidra.androidkotlinretrofitdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Replace with your activity layout resource ID

        textView = findViewById(R.id.mainpage) // Replace with your TextView ID

        // Fetch all posts asynchronously
        lifecycleScope.launch {
            val response = RetrofitInstance.getRetrofitInstance()
                .create(PostRetrofitService::class.java)
                .getPosts()

            if (response.isSuccessful) {
                val posts = response.body() ?: return@launch // Handle empty response
                displayPosts(posts)
            } else {
                // Handle network error
                textView.text = "Error fetching posts"
            }
        }
    }

    private fun displayPosts(posts: List<PostItem>) {
        val stringBuilder = StringBuilder()
        for (post in posts) {
            stringBuilder.append("Post ID: ${post.id}\n")
            stringBuilder.append("Title: ${post.title}\n")
            stringBuilder.append("Body: ${post.body}\n\n")
        }
        textView.text = stringBuilder.toString()
    }
}