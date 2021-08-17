package com.example.githubapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String){
        val listUser = ArrayList<User>()
        val url = "https://api.github.com/search/users?q=$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val resultObject = JSONObject(result)
                    val list = resultObject.getJSONArray("items")
                    for(i in 0 until list.length()){
                        val user = list.getJSONObject(i)
                        val userList = User()
                        userList.id = user.getString("id")
                        userList.username = user.getString("login")
                        userList.photo = user.getString("avatar_url")
                        userList.url = user.getString("html_url")
                        listUser.add(userList)
                    }
                    listUsers.postValue(listUser)
                }catch (e: Exception){
                    Log.d("Error Try ", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("Failure ", error.message.toString())
            }

        })
    }

    fun getUser():LiveData<ArrayList<User>>{
        return listUsers
    }
}