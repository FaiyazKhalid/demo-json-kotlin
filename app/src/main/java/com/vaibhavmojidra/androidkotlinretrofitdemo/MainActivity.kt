package com.vaibhavmojidra.androidkotlinretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.vaibhavmojidra.androidkotlinretrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val retrofitService=RetrofitInstance.getRetrofitInstance().create(PostRetrofitService::class.java)



        val responseLiveData:LiveData<Response<Posts>> = liveData {
            //All
            val response=retrofitService.getPosts()

            //By UserId - Query Parameters
            //val response=retrofitService.getPostsByUserId(3)
            emit(response)
        }
        responseLiveData.observe(this) {
            val postList = it.body()?.listIterator()
            if (postList != null) {
                while (postList.hasNext()) {
                    val postItem = postList.next()
                    val res=" Post UserId: ${postItem.userId}\n Post title: ${postItem.userId}\n Post body: ${postItem.body}\n\n"
                    binding.textView.append(res)
                }
            }
        }

        val responseLiveData2:LiveData<Response<PostItem>> = liveData {
            val response=retrofitService.getPostWithId(2)
            emit(response)
        }

        responseLiveData2.observe(this){
            val postItem=it.body()!!
            Toast.makeText(MainActivity@this,"${postItem.title} \n ${postItem.body}",Toast.LENGTH_LONG).show()
        }
    }
}