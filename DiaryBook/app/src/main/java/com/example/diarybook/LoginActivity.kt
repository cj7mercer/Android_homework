package com.example.diarybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (sharedPreferences.getBoolean("remember_password", false)) {
            account.setText(sharedPreferences.getString("account", ""))
            password.setText(sharedPreferences.getString("password", ""))
            remember_pass.isChecked = true
        }

        login.setOnClickListener {
            val acc: String = account.text.toString()
            val pwd: String = password.text.toString()
            val accounts = sharedPreferences.getString("accounts", "")!!.split("#")
            val pwds = sharedPreferences.getString("passwords", "")!!.split("#")

            if (acc in accounts) {
                val index = accounts.indexOf(acc)
                if (pwds[index] == pwd) {
                    editor.putString("account", acc)
                    editor.putString("password", pwd)
                    if (remember_pass.isChecked) {
                        editor.putBoolean("remember_password", true)
                    }
                    else {
                        editor.putBoolean("remember_password", false)
                    }

                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else {
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show()
            }
        }

    }
}