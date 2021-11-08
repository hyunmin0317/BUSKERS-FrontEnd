package com.example.outstagram

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup1)
        login.setOnClickListener { startActivity(Intent(this@SignupActivity1, LoginActivity1::class.java)) }
        signup.setOnClickListener { register(this@SignupActivity1) }
    }

    fun register(activity: Activity) {
        val username = username_inputbox.text.toString()
        val password1 = password1_inputbox.text.toString()
        val password2 = password2_inputbox.text.toString()

        if (password1 == password2) {
            (application as MasterApplication).service.register(
                username, password1
            ).enqueue(object :
                Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                        val user = response.body()
                        val token = user!!.token!!
                        saveUserToken(username, token, activity)
                        (application as MasterApplication).createRetrofit()
                        activity.startActivity(Intent(activity, OutStagramPostListActivity::class.java))
                    } else {
                        Toast.makeText(activity, "사용할 수 없는 아이디입니다.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_LONG).show()
                }

            })
        } else {
            Toast.makeText(activity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
        }
    }

    fun saveUserToken(username: String, token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("username", username)
        editor.putString("token", token)
        editor.commit()
    }
}