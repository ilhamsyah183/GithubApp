package com.example.githubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        username?.let {
            observeDetailUser(it)
        }


    }

    private fun observeDetailUser(username: String) {
        viewModel.getDetailUser(username)
        viewModel.detailUser.observe(this) { detailUser ->
            binding.apply {
                tvUsername.text = detailUser.login
                Glide.with(this@DetailActivity)
                    .load(detailUser.avatarUrl)
                    .into(imgUser)
                tvCompany.text = detailUser.company
                tvLocation.text = detailUser.location
                tvTotalFollowers.text = detailUser.followers.toString()
                tvTotalFollowing.text = detailUser.following.toString()
                tvTotalRepo.text = detailUser.publicRepos.toString()
                tvName.text = detailUser.name
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }


}