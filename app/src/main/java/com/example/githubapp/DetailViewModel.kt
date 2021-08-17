package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    var _detailUser = MutableLiveData<DetailUser>()
    val detailUser: LiveData<DetailUser> get() = _detailUser

    fun getDetailUser(username: String) {
        val detailUser = DetailUser()
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.apply {
            addHeader("Authorization", "token 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")
            addHeader("User-Agent", "request")
            get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
                ) {
                    try {
                        val result = String(responseBody)
                        val jsonObject = JSONObject(result)
                        detailUser.apply {
                            avatarUrl = jsonObject.getString("avatar_url")
                            company = jsonObject.getString("company")
                            followers = jsonObject.getInt("followers")
                            following = jsonObject.getInt("following")
                            location = jsonObject.getString("location")
                            login = jsonObject.getString("login")
                            name = jsonObject.getString("name")
                            publicRepos = jsonObject.getInt("public_repos")
                        }
                        _detailUser.value = detailUser
                    } catch (e: Exception) {
                        Log.e("Error", e.message.toString())
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
                ) {
                    Log.e("Error", error?.message.toString())
                }
            })
        }
    }
}