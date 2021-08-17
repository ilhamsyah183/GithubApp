package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchView
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        searchView = activityMainBinding.searchView

        recycler = activityMainBinding.rvUser

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory() )
            .get(UserViewModel::class.java)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        userViewModel.getUser().observe(this, { listUser ->
            if(listUser != null){
                adapter.setData(listUser)
                adapter.notifyDataSetChanged()
            }

        })
        searchUser()
    }

    private fun searchUser() {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    return if (query.isEmpty()){
                        true
                    }else{
                        userViewModel.setUser(query)
                        true
                    }
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
    }
}